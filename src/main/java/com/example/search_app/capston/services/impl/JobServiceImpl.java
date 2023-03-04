package com.example.search_app.capston.services.impl;

import com.example.search_app.capston.dto.AddJobDto;
import com.example.search_app.capston.models.Company;
import com.example.search_app.capston.models.Job;
import com.example.search_app.capston.models.Skill;
import com.example.search_app.capston.services.CompanyService;
import com.example.search_app.capston.services.JobService;
import com.example.search_app.capston.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final CompanyService companyService;
    private final SkillService skillService;

    @Override
    public void addJob(AddJobDto addJobDto) {
        Company company = companyService.getCompanyById(addJobDto.getCompanyId());
        List<Skill> skills = skillService.getAllSkillByIds(addJobDto.getSkillIds());

        Job job = new Job();



    }
}
