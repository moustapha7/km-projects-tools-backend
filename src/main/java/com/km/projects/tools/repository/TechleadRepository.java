package com.km.projects.tools.repository;



import com.km.projects.tools.model.Techlead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechleadRepository  extends JpaRepository<Techlead, Long> {
}
