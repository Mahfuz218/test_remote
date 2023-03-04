package com.example.search_app.capston.services;

import com.example.search_app.capston.dto.AddJobDto;
import com.example.search_app.capston.models.Job;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface JobService {
    void addOrUpdateJob(AddJobDto addJobDto);

    Job getJobById(long jobId);

    void deleteJob(long jobId);

    List<Job> getAllJob();

    Page<Job> getPageableJobList(Optional<String> jobType, Optional<String> companyName, int pageNo, int pageSize);
}
