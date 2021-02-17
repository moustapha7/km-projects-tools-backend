package com.km.projects.tools.repository;


import com.km.projects.tools.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByCodeOtp(String code);

    User findUserByUsername(String username);

    @Query(
            value = "SELECT * FROM USERS u, user_roles ur where u.id = ur.user_id ",
            nativeQuery = true)
    List<User> findAllUsers();

}
