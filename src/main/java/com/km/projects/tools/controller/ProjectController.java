package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Project;
import com.km.projects.tools.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public List<Project> getAllProject()
    {
        return projectService.getAllProject();
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") long projectId) throws ResourceNotFoundException
    {
        return  projectService.getProjectById(projectId);
    }


    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@Validated @RequestBody Project project)
    {
        return projectService.createProject(project);
    }

    @DeleteMapping("projects/{id}")
    public Map<String, Boolean> deleteProject(@PathVariable (value = "id") long projectId) throws ResourceNotFoundException
    {
        return projectService.deleteProject(projectId);
    }

    @PutMapping("projects/{id}")
    public  ResponseEntity<Project> updateProject(@PathVariable (value = "id") long id, @RequestBody Project project) throws ResourceNotFoundException
    {
        return projectService.updateProject(id,project);
    }


    @GetMapping(path =  "/photoProject/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhotoProject(@PathVariable Long id) throws Exception {
       return  projectService.getPhotoProject(id);
    }

    @PostMapping(path = "/uploadPhotoProject/{id}")
    public void uploadPhotoProject(MultipartFile file, @PathVariable Long id) throws Exception
    {
        projectService.uploadPhotoProject(file, id);
    }


    @GetMapping("/nombreProjects")
    public long getNombreProjects()
    {
        return  projectService.getNombreProjects();

    }

}
