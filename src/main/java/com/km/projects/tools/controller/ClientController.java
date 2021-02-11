package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Client;
import com.km.projects.tools.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/clients")
    public List<Client> getAllClients()
    {
        return clientService.getAllClients();
    }

    @GetMapping("clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") long clientId) throws ResourceNotFoundException
    {
        return clientService.getClientById(clientId);
    }


    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Validated @RequestBody Client client) throws ResourceNotFoundException
    {
        return clientService.createClient(client);
    }

    @DeleteMapping("clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") long clientId) throws ResourceNotFoundException
    {
        return clientService.deleteClient(clientId);
    }

    @PutMapping("clients/{id}")
    public  ResponseEntity<Client> updateClient(@PathVariable(value = "id") long id, @RequestBody Client client) throws ResourceNotFoundException
    {
        return clientService.updateClient(id,client);
    }

    @GetMapping("/nombreClients")
    public long getNombreClients()
    {
        return  clientService.getNombreClients();

    }


}
