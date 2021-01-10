package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/nombreUsers")
    public long getNombreUsers()
    {
        return userRepository.count();
    }
}
