package com.ironhorse.repository;

import com.ironhorse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Long deleteUserById(Long id);
    User findByEmail(String email);
}