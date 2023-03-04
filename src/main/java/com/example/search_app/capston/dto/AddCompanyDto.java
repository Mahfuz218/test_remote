package com.example.search_app.capston.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class AddCompanyDto {
    @NotNull
    String name;
    @NonNull
    String email;
    @NonNull
    String companyAddress;
    @NonNull
    String phone;
}
