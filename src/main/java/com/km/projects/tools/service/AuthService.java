package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.message.request.CodeOtpRequest;
import com.km.projects.tools.message.request.LoginRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.message.response.JwtResponse;
import com.km.projects.tools.model.Departement;
import com.km.projects.tools.model.ERole;
import com.km.projects.tools.model.Role;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.DepartementRepository;
import com.km.projects.tools.repository.RoleRepository;
import com.km.projects.tools.repository.UserRepository;
import com.km.projects.tools.security.jwt.JwtUtils;
import com.km.projects.tools.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

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
     DepartementRepository departementRepository;



    public List<Departement> getAllDepartements()
    {
        return departementRepository.findAll();
    }


    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) throws ResourceNotFoundException
    {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with username: " + loginRequest.getUsername()));
        if(!user.isActivated())
        {
            throw new ResourceNotFoundException("Votre Compte n'est pas encore activé");
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
                , userDetails.getName()
                ,userDetails.getDepartement()));
    }





    public ResponseEntity<User> registerUser( SignupRequest signUpRequest) throws ResourceNotFoundException
    {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResourceNotFoundException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResourceNotFoundException("Error: Email is already exist!");
        }

        if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new ResourceNotFoundException("Error: Please confirm your password");
        }

        if(signUpRequest.getPassword() == null || signUpRequest.getPassword().equals("")) {
            throw new ResourceNotFoundException("Error: the password must not be empty");
        }


        Random rand = new Random();
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
        String code =  String.format(String.valueOf(rand.nextInt(10000)));

        user.setRoles(roles);
        user.setActivated(false);
        user.setPhotoName("avatar.png");
        user.setProfileUser(roles.toString());
        user.setCodeOtp(code);



        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    public ResponseEntity<User> verifCode(CodeOtpRequest codeOtpRequest) throws ResourceNotFoundException
    {
        Optional<User> userInfo = userRepository.findByUsername(codeOtpRequest.getUsername());
        if(userInfo.isPresent())
        {
            User user1 = userInfo.get();

            if(user1.isActivated() == true)
            {
                throw new ResourceNotFoundException("ce compte est deja activé");
            }

            if( !user1.getCodeOtp().equals(codeOtpRequest.getCodeOtp()) )
            {
                throw new ResourceNotFoundException("Le code otp est incorrect");
            }

            user1.setActivated(true);

            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);
        }
        else
        {
            throw new ResourceNotFoundException("Username not found");
        }

    }


}
