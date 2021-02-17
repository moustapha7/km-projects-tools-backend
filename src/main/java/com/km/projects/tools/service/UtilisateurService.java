package com.km.projects.tools.service;

import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.message.request.ChangePasswordRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.model.*;
import com.km.projects.tools.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UtilisateurService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TechleadRepository techleadRepository;

    @Autowired
    private PownerRepository pownerRepository;

    @Autowired
    private DeveloppeurRepository developpeurRepository;



    //desactive compte by admin
    public ResponseEntity<User> desactiveCompte(long id)
    {
        Optional<User> userInfo = userRepository.findById(id);
        if(userInfo.isPresent())
        {
            User user1 = userInfo.get();
            user1.setActivated(false);
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }


    //active compte by admin
    public ResponseEntity<User> activeCompte(long id)
    {
        Optional<User> userInfo = userRepository.findById(id);
        if(userInfo.isPresent())
        {
            User user1 = userInfo.get();
            user1.setActivated(true);
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }


    public ResponseEntity<User> updateUser( SignupRequest signupRequest) throws ResourceNotFoundException
    {

        Optional<User>  userinfo = userRepository.findByUsername(signupRequest.getUsername());

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new ResourceNotFoundException("Error: Username is already taken!");
        }

        if(userinfo.isPresent())
        {

            User user1 = userinfo.get();

            user1.setFirstname(signupRequest.getFirstname());
            user1.setName(signupRequest.getName());
            user1.setUsername(signupRequest.getUsername());
            user1.setEmail(signupRequest.getEmail());

            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        }
        else
        {
            throw new ResourceNotFoundException("Cet User n'existe pas");
        }

    }

    public ResponseEntity<User> changePassword(ChangePasswordRequest changePasswordRequest) throws ResourceNotFoundException
    {


        Optional<User> userInfo = userRepository.findByUsername(changePasswordRequest.getUsername());

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal. toString();

        }

        if(userInfo.isPresent())
        {

            User user1 = userInfo.get();

            if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword()))
            {
                throw new ResourceNotFoundException("Error: Please confirm your password");
            }

            if(!username.equals(changePasswordRequest.getUsername()))
            {
                throw new ResourceNotFoundException("ce nest pas votre compte");
            }
            user1.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        }
        else
        {
            throw new ResourceNotFoundException("Cet User n'existe pas");
        }

    }


    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }



    public ResponseEntity<User> getUserById( long id) throws ResourceNotFoundException
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user non trouvé"));
        return  ResponseEntity.ok().body(user);

    }


//find user by id for the photo
    public byte[] getPhotoUser( Long id) throws Exception {
        User user = userRepository.findById(id).get();

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/user/"+ user.getPhotoName()));
    }

    //find user by username for the photo
    public byte[] getPhotoUserByUsername( String  username) throws Exception {
        User user = userRepository.findUserByUsername(username);

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/user/"+ user.getPhotoName()));
    }



    public void uploadPhotoUser(MultipartFile file, Long id) throws Exception
    {
        User user = userRepository.findById(id).get();
        user.setPhotoName(user.getName()+id+".png");
        Files.write(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/user/"+ user.getPhotoName()),file.getBytes());
        userRepository.save(user);

    }

    public long getNombreUsers()
    {
        return userRepository.count();
    }


    public  List<Role> getAllRoles()
    {
        return roleRepository.findAll();
    }


    public ResponseEntity<User> updateRoleUser(long id, SignupRequest signupRequest) throws ResourceNotFoundException
    {

        Optional<User>  userinfo = userRepository.findById(id);

        if (!userinfo.isPresent()) {
            throw new ResourceNotFoundException("Error: user not found!");
        }



        User user1 = userinfo.get();


        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role Admin is not found."));
                        roles.add(adminRole);

                        break;
                    case "teachlead":
                        Role teachleadRole = roleRepository.findByName(ERole.ROLE_TEACHLEAD)
                                .orElseThrow(() -> new RuntimeException("Error: Role TEch Lead is not found."));
                        roles.add(teachleadRole);

                        Techlead techlead = new Techlead();
                        techlead.setFirstname(user1.getFirstname());
                        techlead.setName(user1.getName());
                        techlead.setUsername(user1.getUsername());
                        techlead.setEmail(user1.getEmail());
                        techlead.setPhotoName(user1.getPhotoName());
                        techleadRepository.save(techlead);

                        break;
                    case "powner":
                        Role pownerRole = roleRepository.findByName(ERole.ROLE_POWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role Product Owner is not found."));
                        roles.add(pownerRole);

                        Powner powner = new Powner();
                        powner.setFirstname(user1.getFirstname());
                        powner.setName(user1.getName());
                        powner.setUsername(user1.getUsername());
                        powner.setEmail(user1.getEmail());
                        powner.setPhotoName(user1.getPhotoName());
                        pownerRepository.save(powner);

                        break;
                    case "dev":
                        Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                                .orElseThrow(() -> new RuntimeException("Error: Role DEV is not found."));
                        roles.add(devRole);

                        Developpeur developpeur  = new Developpeur();
                        developpeur.setFirstname(user1.getFirstname());
                        developpeur.setName(user1.getName());
                        developpeur.setUsername(user1.getUsername());
                        developpeur.setEmail(user1.getEmail());
                        developpeur.setPhotoName(user1.getPhotoName());
                        developpeurRepository.save(developpeur);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role User is not found."));
                        roles.add(userRole);
                }
            });
        }
        user1.setRoles(roles);
        user1.setProfileUser(roles.toString());


        return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);


    }


    public List<Developpeur> getAllDev()
    {
        return developpeurRepository.findAll();
    }

    public List<Powner> getAllPowner()
    {
        return  pownerRepository.findAll();
    }

    public  List<Techlead> getAllTechLead()
    {
        return techleadRepository.findAll();
    }



}
