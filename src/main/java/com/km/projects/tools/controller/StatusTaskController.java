package com.km.projects.tools.controller;


import com.km.projects.tools.model.StatusTask;
import com.km.projects.tools.repository.StatusTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class StatusTaskController {

    @Autowired
    private StatusTaskRepository statusTaskRepository;


    @GetMapping("/statusTasks")
    private List<StatusTask> getAllStatusTask()
    {
        return statusTaskRepository.findAll();
    }
}
