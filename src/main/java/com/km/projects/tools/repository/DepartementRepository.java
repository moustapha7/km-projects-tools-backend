package com.km.projects.tools.repository;

import com.km.projects.tools.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {


    Optional<Departement> findByName(String name);
}
