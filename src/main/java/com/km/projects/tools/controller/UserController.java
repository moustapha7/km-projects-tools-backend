package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.message.request.ChangePasswordRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.model.*;
import com.km.projects.tools.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    UtilisateurService utilisateurService;


    @GetMapping("/listUsers")
    public List<User> getAllUsers()
    {
        return utilisateurService.getAllUsers();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws ResourceNotFoundException
    {
        return  utilisateurService.getUserById(id);
    }


    @GetMapping(path =  "/photoUser/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhotoUser(@PathVariable Long id) throws Exception
    {
        return utilisateurService.getPhotoUser(id);
    }

    @PostMapping(path = "/uploadPhotoUser/{id}")
    public void uploadPhotoUser(MultipartFile file, @PathVariable Long id) throws Exception
    {
       utilisateurService.uploadPhotoUser(file,id);
    }


    @PutMapping("desactiveUser/{id}")
    public ResponseEntity<User> desactiveCompte(@PathVariable(value = "id") long id)
    {
        return utilisateurService.desactiveCompte(id);
    }


    @PutMapping("activeUser/{id}")
    public ResponseEntity<User> activeCompte(@PathVariable(value = "id") long id)
    {
        return utilisateurService.activeCompte(id);
    }


    @PostMapping("updateUsers")
    public ResponseEntity<User> updateUser( @RequestBody SignupRequest signupRequest) throws ResourceNotFoundException
    {
       return utilisateurService.updateUser(signupRequest);
    }


    @GetMapping("listRoles")
     public  List<Role> getAllRoles()
     {
         return utilisateurService.getAllRoles();
     }


    @PostMapping("/changePassword")
    public ResponseEntity<User> changePassword( @RequestBody ChangePasswordRequest changePasswordRequest) throws ResourceNotFoundException
    {
        return utilisateurService.changePassword(changePasswordRequest);
    }


    @GetMapping("/nombreUsers")
    public long getNombreUsers()
    {
        return utilisateurService.getNombreUsers();
    }

    @PutMapping("/updateRoleUser/{id}")
    public ResponseEntity<User> updateRoleUser(@PathVariable(value = "id") long id, @RequestBody SignupRequest signupRequest) throws ResourceNotFoundException
    {
        return utilisateurService.updateRoleUser(id,signupRequest);
    }

    @GetMapping("/listDeveloppeurs")
    public List<Developpeur> getAllDev()
    {
        return utilisateurService.getAllDev();
    }

    @GetMapping("/listPowners")
    public List<Powner> getAllPowner()
    {
        return utilisateurService.getAllPowner();
    }

    @GetMapping("/listTechleads")
    public List<Techlead> getAllTechlead()
    {
        return utilisateurService.getAllTechLead();
    }



}
