package com.example.search_app.capston.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AddJobDto {
    private String title;
    private long companyId;
    private Set<Long> skillIds;
    private String location;
    private String jobType;
    private String jobDescription;
}
