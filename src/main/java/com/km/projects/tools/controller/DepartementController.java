package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Departement;
import com.km.projects.tools.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class DepartementController {

    @Autowired
    private DepartementRepository departementRepository;

    @GetMapping("/departements")
    public List<Departement> getAllDepartements()
    {
        List<Departement> departements = new ArrayList<>();
        departementRepository.findAll().forEach(departements::add);
        return departements;
    }

    @GetMapping("departements/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable(value = "id") long departementId) throws ResourceNotFoundException
    {
        Departement departement = departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("Departement  non trouvé"));
        return  ResponseEntity.ok().body(departement);

    }


    @PostMapping("/departements")
    public Departement createDepartement(@Validated @RequestBody Departement departement)
    {
        return departementRepository.save(departement);
    }

    @DeleteMapping("departements/{id}")
    public Map<String, Boolean> deleteDepartement(@PathVariable(value = "id") long departementId) throws ResourceNotFoundException
    {
        Departement departement = departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("departement  non trouvé"));

        departementRepository.delete(departement);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

    @PutMapping("departements/{id}")
    public  ResponseEntity<Departement> updateDepartement(@PathVariable(value = "id") long id, @RequestBody Departement departement)
    {
        Optional<Departement> departementInfo = departementRepository.findById(id);

        if (departementInfo.isPresent())
        {
            Departement departement1= departementInfo.get();
            departement1.setName(departement.getName());

            return new ResponseEntity<>(departementRepository.save(departement1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/nombreDepartements")
    public long getNombreDepartements()
    {
        return  departementRepository.count();

    }


}
