package com.km.projects.tools.controller;


import com.km.projects.tools.aop.LogUsername;
import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Client;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.UserRepository;
import com.km.projects.tools.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/listUsers")
    @LogUsername
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("/list")
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws ResourceNotFoundException
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user non trouvé"));
        return  ResponseEntity.ok().body(user);

    }


    @GetMapping(path =  "/photoUser/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhotoUser(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).get();

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/user/"+ user.getPhotoName()));
    }

    @PostMapping(path = "/uploadPhotoUser/{id}")
    public void uploadPhotoUser(MultipartFile file, @PathVariable Long id) throws Exception
    {
        User user = userRepository.findById(id).get();
        user.setPhotoName(user.getName()+id+".png");
        Files.write(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/user/"+ user.getPhotoName()),file.getBytes());
        userRepository.save(user);

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





    @GetMapping("/nombreUsers")
    public long getNombreUsers()
    {
        return userRepository.count();
    }
}
