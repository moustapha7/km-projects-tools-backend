package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Departement;
import com.km.projects.tools.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;


    public List<Departement> getAllDepartements()
    {
        List<Departement> departements = new ArrayList<>();
        departementRepository.findAll().forEach(departements::add);
        return departements;
    }


    public ResponseEntity<Departement> getDepartementById(long departementId) throws ResourceNotFoundException
    {
        Departement departement = departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("Departement  non trouvé"));
        return  ResponseEntity.ok().body(departement);

    }



    public Departement createDepartement(Departement departement)
    {
        return departementRepository.save(departement);
    }


    public Map<String, Boolean> deleteDepartement(long departementId) throws ResourceNotFoundException
    {
        Departement departement = departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("departement  non trouvé"));

        departementRepository.delete(departement);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }


    public  ResponseEntity<Departement> updateDepartement(long id,  Departement departement) throws ResourceNotFoundException
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
           throw new  ResourceNotFoundException("departement  non trouvé");
        }

    }

    public long getNombreDepartements()
    {
        return  departementRepository.count();

    }

}
