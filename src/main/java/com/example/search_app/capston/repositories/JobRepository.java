package com.example.search_app.capston.repositories;

import org.perscholas.search_job_app.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {


}
