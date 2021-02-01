package com.km.projects.tools.service;

import com.km.projects.tools.message.request.LoginRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.message.response.JwtResponse;
import com.km.projects.tools.message.response.MessageResponse;
import com.km.projects.tools.model.*;
import com.km.projects.tools.repository.CodeOtpRepository;
import com.km.projects.tools.repository.DepartementRepository;
import com.km.projects.tools.repository.RoleRepository;
import com.km.projects.tools.repository.UserRepository;
import com.km.projects.tools.security.jwt.JwtUtils;
import com.km.projects.tools.security.services.UserDetailsImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UtilisateurService utilisateurService;


    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private CodeOtpRepository codeOtpRepository;


    public List<Departement> getAllDepartements()
    {
        return departementRepository.findAll();
    }


    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest)
    {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));
        if(!user.isActivated())
        {
            return  ResponseEntity.status(400).body("Votre Compte n'est pas encore activé");
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt
                ,userDetails.getId()
                , userDetails.getUsername()
                , userDetails.getEmail()
                ,  roles
                ,userDetails.getFirstname()
                , userDetails.getName()));
    }





    public ResponseEntity<?> registerUser( SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Please confirm your password"));
        }

        if(signUpRequest.getPassword() == null || signUpRequest.getPassword().equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: the password must not be empty"));
        }



        // Create new user's account
        User user = new User();

        user.setFirstname(signUpRequest.getFirstname());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setDepartement(signUpRequest.getDepartement());


        Set<String> strRoles = signUpRequest.getRole();
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

                        break;
                    case "powner":
                        Role pownerRole = roleRepository.findByName(ERole.ROLE_POWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role Product Owner is not found."));
                        roles.add(pownerRole);

                        break;
                    case "dev":
                        Role devRole = roleRepository.findByName(ERole.ROLE_DEV)
                                .orElseThrow(() -> new RuntimeException("Error: Role DEV is not found."));
                        roles.add(devRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role User is not found."));
                        roles.add(userRole);
                }
            });
        }


        user.setRoles(roles);
        user.setActivated(false);
        user.setPhotoName("avatar.png");
        user.setProfileUser(roles.toString());
        userRepository.save(user);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    public CodeOtp saveCode(CodeOtp codeOtp)
    {
        Random rand = new Random();
        int code = rand.nextInt(10000);
        codeOtp.setDateGene(ZonedDateTime.now());
        codeOtp.setCode(code);
        codeOtpRepository.save(codeOtp);

        return codeOtp;
    }

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


    public ResponseEntity<User> updateUser(long id, User user)
    {

        Optional<User>  userinfo = userRepository.findById(id);


        if(userinfo.isPresent())
        {

            User user1 = userinfo.get();

            user1.setFirstname(user.getFirstname());
            user1.setName(user.getName());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setDepartement(user.getDepartement());

            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }



}
