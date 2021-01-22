package com.km.projects.tools.repository;


import com.km.projects.tools.model.CodeOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeOtpRepository extends JpaRepository<CodeOtp, Long> {
}
