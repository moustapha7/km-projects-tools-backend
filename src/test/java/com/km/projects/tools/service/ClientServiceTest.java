package com.km.projects.tools.service;

import com.km.projects.tools.model.Client;
import com.km.projects.tools.repository.ClientRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Mock
    RestTemplate restTemplate;

    @Test
    void createClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setCode("cli1");
        client.setAdresse("dakar");
        client.setEmail("ada@gmail.com");
        client.setTel("77 544 11 66");
        client.setNom("tata");
        client.setPrenom("mama");

        when(clientRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(client);

        ResponseEntity<Client> created = clientService.createClient(client);

      //  Assert.assertEquals(created.getId()).isSameAs(client.getId());
        verify(clientRepository).save(client);
        assertNotNull(client);
    }

}