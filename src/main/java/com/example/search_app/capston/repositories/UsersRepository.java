package com.example.search_app.capston.repositories;

import com.example.search_app.capston.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsernameEqualsIgnoreCase(String username);
    Optional<Users> findByEmailEqualsIgnoreCase(String email);
}
