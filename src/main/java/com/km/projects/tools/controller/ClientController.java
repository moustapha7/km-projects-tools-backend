package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Client;
import com.km.projects.tools.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/clients")
    public List<Client> getAllClients()
    {
        List<Client> clients = new ArrayList<>();
        clientRepository.findAll().forEach(clients::add);
        return clients;
    }

    @GetMapping("clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") long clientId) throws ResourceNotFoundException
    {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("client non trouvé"));
        return  ResponseEntity.ok().body(client);

    }


    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Validated @RequestBody Client client)
    {

        Random rand = new Random();
        String codeClient = String.format("Cli_"+rand.nextInt(100));

        client.setCode(codeClient);


        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
    }

    @DeleteMapping("clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") long clientId) throws ResourceNotFoundException
    {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("client non trouvé"));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

    @PutMapping("clients/{id}")
    public  ResponseEntity<Client> updateClient(@PathVariable(value = "id") long id, @RequestBody Client client)
    {
        Optional<Client> clientInfo = clientRepository.findById(id);

        if (clientInfo.isPresent())
        {
            Client client1= clientInfo.get();
            client1.setCode(client.getCode());
            client1.setAdresse(client.getAdresse());
            client1.setTel(client.getTel());
            client1.setEmail(client.getEmail());
            client1.setPrenom(client.getPrenom());
            client1.setNom(client.getNom());

            return new ResponseEntity<>(clientRepository.save(client1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/nombreClients")
    public long getNombreClients()
    {
        return  clientRepository.count();

    }


}
