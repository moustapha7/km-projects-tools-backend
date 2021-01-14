package com.km.projects.tools.service;

import com.km.projects.tools.message.request.LoginRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.message.response.JwtResponse;
import com.km.projects.tools.message.response.MessageResponse;
import com.km.projects.tools.model.Departement;
import com.km.projects.tools.model.ERole;
import com.km.projects.tools.model.Role;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.DepartementRepository;
import com.km.projects.tools.repository.RoleRepository;
import com.km.projects.tools.repository.UserRepository;
import com.km.projects.tools.security.jwt.JwtUtils;
import com.km.projects.tools.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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


    public List<Departement> getAllDepartements()
    {
        return departementRepository.findAll();
    }


    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest)
    {
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
                ,  roles));
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
        user.setActivated(true);
        user.setProfileUser(roles.toString());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }



}
