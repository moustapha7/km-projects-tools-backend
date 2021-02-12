package com.km.projects.tools.controller;

import com.km.projects.tools.KmProjectsToolsApplication;
import com.km.projects.tools.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmProjectsToolsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port=8080;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void getAllClients() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Client> entity = new HttpEntity<Client>(null, headers);

        ResponseEntity<Client> response = restTemplate.exchange(getRootUrl() + "/api/clients",
                HttpMethod.GET, entity, Client.class);

        assertNotNull(response.getBody());
    }

  @Test
    void getClientById() {
      Client client = restTemplate.getForObject(getRootUrl() + "/api/clients/1", Client.class);
      System.out.println(client.getPrenom());
      assertNotNull(client);
    }


    @Test
    void createClient() {
        Client client = new Client();
        client.setId(1L);
        client.setCode("cli1");
        client.setAdresse("dakar");
        client.setEmail("ada@gmail.com");
        client.setTel("77 544 11 66");
        client.setNom("tata");
        client.setPrenom("mama");
        ResponseEntity<Client> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/clients", client, Client.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }


    @Test
    void deleteClient() {
        int id = 2;
        Client client = restTemplate.getForObject(getRootUrl() + "/api/clients/" + id, Client.class);
        assertNotNull(client);
        restTemplate.delete(getRootUrl() + "/api/clients/" + id);
        try {
            client = restTemplate.getForObject(getRootUrl() + "/api/clients/" + id, Client.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void updateClient() {
        int id = 1;
        Client client = restTemplate.getForObject(getRootUrl() + "/api/clients/" + id, Client.class);
        client.setCode("cli1");
        client.setAdresse("dakar");
        client.setEmail("ada@gmail.com");
        client.setTel("77 544 11 66");
        client.setNom("tata");
        client.setPrenom("tata");
        restTemplate.put(getRootUrl() + "/api/clients/" + id,client);
        Client updateClient = restTemplate.getForObject(getRootUrl() + "/api/clients/" + id, Client.class);
        assertNotNull(updateClient);
    }

    @Test
    void getNombreClients() {
    }
}