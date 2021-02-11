package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.ProjectType;
import com.km.projects.tools.repository.ProjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectTypeService {


    @Autowired
    private ProjectTypeRepository projectTypeRepository;


    public List<ProjectType> getAllProjectType()
    {
        List<ProjectType> projectTypes = new ArrayList<>();
        projectTypeRepository.findAll().forEach(projectTypes::add);
        return projectTypes;
    }


    public ResponseEntity<ProjectType> getProjectTypeById( long projectTypeId) throws ResourceNotFoundException
    {
        ProjectType projectType = projectTypeRepository.findById(projectTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("project Type non trouvé"));
        return  ResponseEntity.ok().body(projectType);

    }


    public ProjectType createProjectType( ProjectType projectType)
    {
        return projectTypeRepository.save(projectType);
    }


    public Map<String, Boolean> deleteProjectType( long projectTypeId) throws ResourceNotFoundException
    {
        ProjectType projectType = projectTypeRepository.findById(projectTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Project Type not found"));

        projectTypeRepository.delete(projectType);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }


    public  ResponseEntity<ProjectType> updateProjectType( long id, ProjectType projectType) throws ResourceNotFoundException
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
            throw new ResourceNotFoundException("project type not found");
        }

    }

    public long getNombreProjectTypes()
    {
        return  projectTypeRepository.count();

    }

}
