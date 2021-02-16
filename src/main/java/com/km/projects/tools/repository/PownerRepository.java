package com.km.projects.tools.repository;



import com.km.projects.tools.model.Powner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PownerRepository  extends JpaRepository<Powner, Long>{
}
