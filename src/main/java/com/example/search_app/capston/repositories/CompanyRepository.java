package com.example.search_app.capston.repositories;

import com.example.search_app.capston.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByNameEqualsIgnoreCase(String name);
}
