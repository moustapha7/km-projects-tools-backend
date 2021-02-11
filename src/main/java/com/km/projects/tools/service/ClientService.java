package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Client;
import com.km.projects.tools.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;



    public List<Client> getAllClients()
    {
        List<Client> clients = new ArrayList<>();
        clientRepository.findAll().forEach(clients::add);
        return clients;
    }


    public ResponseEntity<Client> getClientById( long clientId) throws ResourceNotFoundException
    {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("client non trouvé"));
        return  ResponseEntity.ok().body(client);

    }



    public ResponseEntity<Client> createClient( Client client) throws ResourceNotFoundException
    {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ResourceNotFoundException("Error: Email is already taken!");
        }

        if (clientRepository.existsByTel(client.getTel())) {
            throw new ResourceNotFoundException("Error: Ce numero téléphone existe déja!");
        }

        Random rand = new Random();
        String codeClient = String.format("Cli_"+rand.nextInt(100));

        client.setCode(codeClient);


        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
    }


    public Map<String, Boolean> deleteClient( long clientId) throws ResourceNotFoundException
    {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("client non trouvé"));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }

    public  ResponseEntity<Client> updateClient( long id, Client client) throws ResourceNotFoundException
    {


        Optional<Client> clientInfo = clientRepository.findById(id);

        if (clientInfo.isPresent() && client.getEmail().equals(client.getEmail()))
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
            throw new ResourceNotFoundException("Error: Email is already taken!");
        }

    }

    public long getNombreClients()
    {
        return  clientRepository.count();

    }
}
