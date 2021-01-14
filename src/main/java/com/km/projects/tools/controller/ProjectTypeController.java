package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.ProjectType;
import com.km.projects.tools.repository.ProjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ProjectTypeController {

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @GetMapping("/projectTypes")
    public List<ProjectType> getAllProjectType()
    {
        List<ProjectType> projectTypes = new ArrayList<>();
        projectTypeRepository.findAll().forEach(projectTypes::add);
        return projectTypes;
    }

    @GetMapping("projectTypes/{id}")
    public ResponseEntity<ProjectType> getProjectTypeById(@PathVariable(value = "id") long projectTypeId) throws ResourceNotFoundException
    {
        ProjectType projectType = projectTypeRepository.findById(projectTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("project Type non trouvé"));
        return  ResponseEntity.ok().body(projectType);

    }


    @PostMapping("/projectTypes")
    public ProjectType createProjectType(@Validated @RequestBody ProjectType projectType)
    {
        return projectTypeRepository.save(projectType);
    }

    @DeleteMapping("projectTypes/{id}")
    public Map<String, Boolean> deleteProjectType(@PathVariable(value = "id") long projectTypeId) throws ResourceNotFoundException
    {
        ProjectType projectType = projectTypeRepository.findById(projectTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Project Type non trouvé"));

        projectTypeRepository.delete(projectType);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

    @PutMapping("projectTypes/{id}")
    public  ResponseEntity<ProjectType> updateProjectType(@PathVariable(value = "id") long id, @RequestBody ProjectType projectType)
    {
        Optional<ProjectType> projectTypeInfo = projectTypeRepository.findById(id);

        if (projectTypeInfo.isPresent())
        {
            ProjectType projectType1= projectTypeInfo.get();
            projectType1.setName(projectType.getName());

            return new ResponseEntity<>(projectTypeRepository.save(projectType1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/nombreProjectTypes")
    public long getNombreProjectType1s()
    {
        return  projectTypeRepository.count();

    }


}
