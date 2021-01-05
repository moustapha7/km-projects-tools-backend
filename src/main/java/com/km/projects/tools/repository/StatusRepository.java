package com.km.projects.tools.repository;


import com.km.projects.tools.model.StatusProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusProject, Long> {
}
