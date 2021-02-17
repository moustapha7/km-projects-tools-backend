package com.km.projects.tools.repository;



import com.km.projects.tools.model.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusTaskRepository extends JpaRepository<StatusTask, Long> {
}
