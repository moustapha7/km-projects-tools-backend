package com.km.projects.tools.repository;



import com.km.projects.tools.model.Developpeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloppeurRepository  extends JpaRepository<Developpeur, Long> {
}
