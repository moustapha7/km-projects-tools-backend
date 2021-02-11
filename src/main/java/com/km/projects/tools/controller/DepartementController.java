package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Departement;
import com.km.projects.tools.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @GetMapping("/departements")
    public List<Departement> getAllDepartements()
    {
        return departementService.getAllDepartements();
    }

    @GetMapping("departements/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable(value = "id") long departementId) throws ResourceNotFoundException
    {
        return  departementService.getDepartementById(departementId);

    }


    @PostMapping("/departements")
    public Departement createDepartement(@Validated @RequestBody Departement departement)
    {
        return departementService.createDepartement(departement);
    }

    @DeleteMapping("departements/{id}")
    public Map<String, Boolean> deleteDepartement(@PathVariable(value = "id") long departementId) throws ResourceNotFoundException
    {
        return departementService.deleteDepartement(departementId);
    }

    @PutMapping("departements/{id}")
    public  ResponseEntity<Departement> updateDepartement(@PathVariable(value = "id") long id, @RequestBody Departement departement) throws ResourceNotFoundException
    {
        return departementService.updateDepartement(id,departement);
    }

    @GetMapping("/nombreDepartements")
    public long getNombreDepartements()
    {
        return  departementService.getNombreDepartements();

    }


}
