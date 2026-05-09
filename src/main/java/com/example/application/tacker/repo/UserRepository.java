package com.example.application.tacker.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.tacker.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
