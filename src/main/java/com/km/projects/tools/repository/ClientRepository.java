package com.km.projects.tools.repository;


import com.km.projects.tools.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByTel(String tel);

    Boolean existsByEmail(String email);
}
