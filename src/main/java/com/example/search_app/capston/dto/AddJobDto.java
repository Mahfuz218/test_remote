package com.example.search_app.capston.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class AddJobDto {
    @NotNull
    private String title;
    @NotNull
    private Long companyId;
    @NotNull
    @NotEmpty
    private Set<Long> skillIds;
    @NotNull
    private String location;
    @NotNull
    private String jobType;
    @NotNull
    private String jobDescription;

    // for update purpose
    private Long jobId;
}
