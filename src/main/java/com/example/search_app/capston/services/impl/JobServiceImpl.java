package com.example.search_app.capston.services.impl;

import com.example.search_app.capston.dto.AddJobDto;
import com.example.search_app.capston.models.Company;
import com.example.search_app.capston.models.Job;
import com.example.search_app.capston.models.Skill;
import com.example.search_app.capston.models.Users;
import com.example.search_app.capston.repositories.JobRepository;
import com.example.search_app.capston.services.CompanyService;
import com.example.search_app.capston.services.JobService;
import com.example.search_app.capston.services.SkillService;
import com.example.search_app.capston.services.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Sets;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final CompanyService companyService;
    private final SkillService skillService;
    private final UsersService usersService;

    private final JobRepository jobRepository;
    private final EntityManager entityManager;

    @Override
    public void addOrUpdateJob(AddJobDto addJobDto) {
        // getting user info from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersService.getUsersByUserName(authentication.getName());

        Company company = companyService.getCompanyById(addJobDto.getCompanyId());
        List<Skill> skills = skillService.getAllSkillByIds(addJobDto.getSkillIds());

        Job job = new Job();
        // if id is present, update it otherwise new job will be created
        if (addJobDto.getJobId() != null) {
            job = getJobById(addJobDto.getJobId());
        }
        job.setSkills(new HashSet<>(skills));
        job.setCompany(company);
        job.setJobDescription(addJobDto.getJobDescription());
        job.setJobType(addJobDto.getJobType());
        job.setTitle(addJobDto.getTitle());
        job.setLocation(addJobDto.getLocation());
        job.setCreateBy(users);

        jobRepository.save(job);
    }

    @Override
    public Job getJobById(long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job is not found with id:"+jobId));
    }

    @Override
    public void deleteJob(long jobId) {
        Job job = getJobById(jobId);
        jobRepository.delete(job);
    }

    @Override
    public List<Job> getAllJob() {
        return jobRepository.findAll();
    }

    @Override
    public Page<Job> getPageableJobList(Optional<String> jobType, Optional<String> companyName, int pageNo, int pageSize) {
        return jobRepository.findAll(jobFilterSpec(jobType, companyName),
                PageRequest.of(pageNo, pageSize));
    }

    static Specification<Job> jobFilterSpec(Optional<String> jobType, Optional<String> companyName) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            jobType.ifPresent(s -> predicates.add(builder.like(root.get("jobType"), s)));

            companyName.ifPresent(cn -> {
                Join<Job, Company> nestedJoin = root.join("company");
                predicates.add(builder.like(nestedJoin.get("name"), cn));
            });
            // AND all predicates
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
