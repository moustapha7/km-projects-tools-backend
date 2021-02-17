package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Task;
import com.km.projects.tools.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getAllTask()
    {
        return taskService.getAllTasks();
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") long taskId) throws ResourceNotFoundException
    {
        return  taskService.getTaskById(taskId);
    }


    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@Validated @RequestBody Task task)
    {
        return taskService.createTask(task);
    }

    @DeleteMapping("tasks/{id}")
    public Map<String, Boolean> deleteTask(@PathVariable (value = "id") long taskId) throws ResourceNotFoundException
    {
        return taskService.deleteTask(taskId);
    }

    @PutMapping("tasks/{id}")
    public  ResponseEntity<Task> updateTask(@PathVariable (value = "id") long id, @RequestBody Task task) throws ResourceNotFoundException
    {
        return taskService.updateTask(id,task);
    }


    @GetMapping("/nombreTasks")
    public long getNombreTasks()
    {
        return  taskService.getNombreTasks();

    }

}
