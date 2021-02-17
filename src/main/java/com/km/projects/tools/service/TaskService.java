package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Task;
import com.km.projects.tools.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks ()
    {
        return  taskRepository.findAll();
    }

    public ResponseEntity<Task> getTaskById(long id) throws ResourceNotFoundException
    {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return  ResponseEntity.ok().body(task);
    }

    public ResponseEntity<Task> createTask(Task task) throws ResourceNotFoundException
    {
        if(task.getName() == null)
        {
            throw new ResourceNotFoundException("Name can not be null");
        }

        if(task.getDescription() == null)
        {
            throw new ResourceNotFoundException("Description can not be null");
        }

        if(task.getDateDebut() == null)
        {
            throw new ResourceNotFoundException("Date debut can not be null");
        }

        if(task.getDateFin()== null)
        {
            throw new ResourceNotFoundException("Date fin can not be null");
        }

        if(task.getDeveloppeur() == null)
        {
            throw new ResourceNotFoundException("pleaz select a developer");
        }

        if(task.getProject() == null)
        {
            throw new ResourceNotFoundException("pleaz select a project");
        }

        if(task.getStatusTask() == null)
        {
            throw new ResourceNotFoundException("pleaz select a status task");
        }

        Date dateD ;
        Date dateF;

        dateF = task.getDateFin();
        dateD =task.getDateDebut();

        long duration = dateF.getTime() -  dateD.getTime() ;
        long nbreJour = TimeUnit.MILLISECONDS.toDays(duration);;
        long nbreheure = TimeUnit.MILLISECONDS.toHours(duration);;

        task.setEstimationJour(nbreJour);
        task.setEstimationHeure(nbreheure);

        return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);

    }

    public Map<String, Boolean> deleteTask(long taskId) throws ResourceNotFoundException
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("task  not found"));

        taskRepository.delete(task);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

    public  ResponseEntity<Task> updateTask( long id,Task task) throws ResourceNotFoundException
    {
        Optional<Task> taskInfo = taskRepository.findById(id);

        Date dateD ;
        Date dateF;

        dateF = task.getDateFin();
        dateD =task.getDateDebut();

        long duration = dateF.getTime() -  dateD.getTime() ;
        long nbreJour = TimeUnit.MILLISECONDS.toDays(duration);;
        long nbreheure = TimeUnit.MILLISECONDS.toHours(duration);;

        if (taskInfo.isPresent())
        {

            if(task.getName() == null)
            {
                throw new ResourceNotFoundException("Name can not be null");
            }

            if(task.getDescription() == null)
            {
                throw new ResourceNotFoundException("Description can not be null");
            }

            if(task.getDateDebut() == null)
            {
                throw new ResourceNotFoundException("Date debut can not be null");
            }

            if(task.getDateFin()== null)
            {
                throw new ResourceNotFoundException("Date fin can not be null");
            }

            if(task.getDeveloppeur() == null)
            {
                throw new ResourceNotFoundException("pleaz select a developer");
            }

            if(task.getProject() == null)
            {
                throw new ResourceNotFoundException("pleaz select a project");
            }

            if(task.getStatusTask() == null)
            {
                throw new ResourceNotFoundException("pleaz select a status task");
            }
            Task task1= taskInfo.get();

            task1.setName(task.getName());
            task1.setDescription(task.getDescription());
            task1.setDateDebut(task.getDateDebut());
            task1.setDateFin(task.getDateFin());
            task1.setEstimationJour(nbreJour);
            task1.setEstimationHeure(nbreheure);

            task1.setDeveloppeur(task.getDeveloppeur());
            task1.setProject(task.getProject());
            task1.setStatusTask(task.getStatusTask());

            return new ResponseEntity<>(taskRepository.save(task1), HttpStatus.OK);

        }
        else
        {
            throw  new ResourceNotFoundException("task not found");
        }

    }

    public long getNombreTasks()
    {
        return  taskRepository.count();

    }



}
