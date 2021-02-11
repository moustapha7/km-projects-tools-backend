package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.ProjectType;
import com.km.projects.tools.service.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ProjectTypeController {

    @Autowired
    private ProjectTypeService projectTypeService;

    @GetMapping("/projectTypes")
    public List<ProjectType> getAllProjectType()
    {
        return projectTypeService.getAllProjectType();
    }

    @GetMapping("projectTypes/{id}")
    public ResponseEntity<ProjectType> getProjectTypeById(@PathVariable(value = "id") long projectTypeId) throws ResourceNotFoundException
    {
        return  projectTypeService.getProjectTypeById(projectTypeId);
    }


    @PostMapping("/projectTypes")
    public ProjectType createProjectType(@Validated @RequestBody ProjectType projectType)
    {
        return projectTypeService.createProjectType(projectType);
    }

    @DeleteMapping("projectTypes/{id}")
    public Map<String, Boolean> deleteProjectType(@PathVariable(value = "id") long projectTypeId) throws ResourceNotFoundException
    {
        return projectTypeService.deleteProjectType(projectTypeId);
    }

    @PutMapping("projectTypes/{id}")
    public  ResponseEntity<ProjectType> updateProjectType(@PathVariable(value = "id") long id, @RequestBody ProjectType projectType) throws ResourceNotFoundException
    {
        return projectTypeService.updateProjectType(id,projectType);
    }

    @GetMapping("/nombreProjectTypes")
    public long getNombreProjectType1s()
    {
        return  projectTypeService.getNombreProjectTypes();
    }


}
