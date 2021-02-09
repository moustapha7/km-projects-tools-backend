package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.message.request.ChangePasswordRequest;
import com.km.projects.tools.message.request.CodeOtpRequest;
import com.km.projects.tools.message.request.SignupRequest;
import com.km.projects.tools.message.response.MessageResponse;
import com.km.projects.tools.model.Client;
import com.km.projects.tools.model.ERole;
import com.km.projects.tools.model.Role;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.RoleRepository;
import com.km.projects.tools.repository.UserRepository;
import com.km.projects.tools.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/listUsers")
    public List<User> getAllUsers()
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


    @PostMapping("updateUsers")
    public ResponseEntity<User> updateUser( @RequestBody SignupRequest signupRequest) throws ResourceNotFoundException
    {
       return utilisateurService.updateUser(signupRequest);
    }


    @GetMapping("listRoles")
     public  List<Role> getAllRoles()
     {
         return roleRepository.findAll();
     }



    @GetMapping("/list")
    public List<User> getAllUserss()
    {
        return userRepository.findAllUsers();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<User> changePassword( @RequestBody ChangePasswordRequest changePasswordRequest) throws ResourceNotFoundException
    {
        return utilisateurService.changePassword(changePasswordRequest);
    }


    @GetMapping("/nombreUsers")
    public long getNombreUsers()
    {
        return userRepository.count();
    }
}
