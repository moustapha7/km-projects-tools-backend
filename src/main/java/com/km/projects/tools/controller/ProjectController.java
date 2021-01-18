package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Project;
import com.km.projects.tools.model.User;
import com.km.projects.tools.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public List<Project> getAllProject()
    {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") long projectId) throws ResourceNotFoundException
    {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project non trouvé"));
        return  ResponseEntity.ok().body(project);

    }


    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@Validated @RequestBody Project project)
    {

        Date dateD ;
        Date dateF;

        dateF = project.getDateFin();
        dateD =project.getDateDebut();

        long duration = dateF.getTime() -  dateD.getTime() ;
        long nbreJour = TimeUnit.MILLISECONDS.toDays(duration);;
        long nbreheure = TimeUnit.MILLISECONDS.toHours(duration);;

        project.setEstimationJour(nbreJour);
        project.setEstimationHeure(nbreheure);

        return new ResponseEntity<>(projectRepository.save(project), HttpStatus.OK);

    }

    @DeleteMapping("projects/{id}")
    public Map<String, Boolean> deleteProject(@PathVariable(value = "id") long projectId) throws ResourceNotFoundException
    {
        Project Project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project  non trouvé"));

        projectRepository.delete(Project);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

    @PutMapping("projects/{id}")
    public  ResponseEntity<Project> updateProject(@PathVariable(value = "id") long id, @RequestBody Project project)
    {
        Optional<Project> projectInfo = projectRepository.findById(id);

        Date dateD ;
        Date dateF;

        dateF = project.getDateFin();
        dateD =project.getDateDebut();

        long duration = dateF.getTime() -  dateD.getTime() ;
        long nbreJour = TimeUnit.MILLISECONDS.toDays(duration);;
        long nbreheure = TimeUnit.MILLISECONDS.toHours(duration);;

        if (projectInfo.isPresent())
        {
            Project project1= projectInfo.get();

            project1.setName(project.getName());
            project1.setDescription(project.getDescription());
            project1.setDateDebut(project.getDateDebut());
            project1.setDateFin(project.getDateFin());
            project1.setEstimationJour(nbreJour);
            project1.setEstimationHeure(nbreheure);

            project1.setTeam(project.getTeam());
            project1.setClient(project.getClient());
            project1.setUserpo(project.getUserpo());
            project1.setUserteach(project.getUserteach());
            project1.setProjectType(project.getProjectType());
            project1.setStatusProject(project.getStatusProject());

            return new ResponseEntity<>(projectRepository.save(project1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping(path =  "/photoProject/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhotoProject(@PathVariable Long id) throws Exception {
        Project project = projectRepository.findById(id).get();

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/project/"+ project.getPhotoName()));
    }

    @PostMapping(path = "/uploadPhotoProject/{id}")
    public void uploadPhotoProject(MultipartFile file, @PathVariable Long id) throws Exception
    {
        Project project = projectRepository.findById(id).get();
        project.setPhotoName(project.getName()+id+".png");
        Files.write(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/project/"+ project.getPhotoName()),file.getBytes());
        projectRepository.save(project);

    }





    @GetMapping("/nombreProjects")
    public long getNombreProjects()
    {
        return  projectRepository.count();

    }

}
