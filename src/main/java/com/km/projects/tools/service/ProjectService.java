package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Project;
import com.km.projects.tools.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public List<Project> getAllProject()
    {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }


    public ResponseEntity<Project> getProjectById( long projectId) throws ResourceNotFoundException
    {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project non trouvé"));
        return  ResponseEntity.ok().body(project);

    }



    public ResponseEntity<Project> createProject( Project project)
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


    public Map<String, Boolean> deleteProject(long projectId) throws ResourceNotFoundException
    {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project  not found"));

        projectRepository.delete(project);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }


    public  ResponseEntity<Project> updateProject( long id,Project project) throws ResourceNotFoundException
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
            throw  new ResourceNotFoundException("project not found");
        }

    }


    public byte[] getPhotoProject( Long id) throws Exception {
        Project project = projectRepository.findById(id).get();

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/project/"+ project.getPhotoName()));
    }

    public void uploadPhotoProject(MultipartFile file, Long id) throws Exception
    {
        Project project = projectRepository.findById(id).get();
        project.setPhotoName(project.getName()+id+".png");
        Files.write(Paths.get(System.getProperty("user.home")+"/images_km_projects_tools/project/"+ project.getPhotoName()),file.getBytes());
        projectRepository.save(project);

    }



    public long getNombreProjects()
    {
        return  projectRepository.count();

    }
}
