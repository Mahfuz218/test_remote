package com.example.search_app.capston.services.impl;

import com.example.search_app.capston.dto.AddJobDto;
import com.example.search_app.capston.models.Job;
import com.example.search_app.capston.services.JobService;
import com.example.search_app.capston.services.SkillService;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    private CompanyService companyService;
    private SkillService skillService;

    @Override
    public void addJob(AddJobDto addJobDto) {

        Job job = new Job();


    }
}
