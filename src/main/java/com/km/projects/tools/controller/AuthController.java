package com.km.projects.tools.controller;



import com.km.projects.tools.message.request.LoginRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.model.Departement;
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
    UtilisateurService utilisateurService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.debug("Authentification {} :", loginRequest);

        return ResponseEntity.ok(utilisateurService.authenticateUser(loginRequest).getBody());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


        return ResponseEntity.ok( utilisateurService.registerUser(signUpRequest));
    }

  /*  @PostMapping("/signupClient")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignupRequest signUpRequest) {


        return ResponseEntity.ok(utilisateurService.registerClient(signUpRequest));
    }*/


    @GetMapping("/listDepartement")
    public List<Departement> lisDepartement()
    {
        return utilisateurService.getAllDepartements();
    }


}
