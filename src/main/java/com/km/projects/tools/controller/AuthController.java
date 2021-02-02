package com.km.projects.tools.controller;



import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.message.request.CodeOtpRequest;
import com.km.projects.tools.message.request.LoginRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.message.response.JwtResponse;
import com.km.projects.tools.model.Departement;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.UserRepository;
import com.km.projects.tools.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilisateurService utilisateurService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws ResourceNotFoundException {
        logger.debug("Authentification {} :", loginRequest);

        return ResponseEntity.ok(utilisateurService.authenticateUser(loginRequest).getBody());
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws ResourceNotFoundException{


        return ResponseEntity.ok( utilisateurService.registerUser(signUpRequest).getBody());
    }



    @GetMapping("/listDepartement")
    public List<Departement> lisDepartement()
    {
        return utilisateurService.getAllDepartements();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws ResourceNotFoundException
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user non trouvé"));
        return  ResponseEntity.ok().body(user);

    }

    @PostMapping("/verifUsers")
    public ResponseEntity<User> verifUser( @RequestBody CodeOtpRequest codeOtpRequest) throws ResourceNotFoundException
    {
        return utilisateurService.verifCode(codeOtpRequest);
    }

}
