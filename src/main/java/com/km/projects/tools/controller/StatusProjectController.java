package com.km.projects.tools.controller;


import com.km.projects.tools.model.StatusProject;
import com.km.projects.tools.repository.StatusProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class StatusProjectController {

    @Autowired
    private StatusProjectRepository statusProjectRepository;


    @GetMapping("/statusProjects")
    private List<StatusProject> getAllSatusProject()
    {
        return statusProjectRepository.findAll();
    }
}
