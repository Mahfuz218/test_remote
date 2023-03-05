package com.example.search_app.capston.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCompanyDto {

    //Long id;
    @NotNull
    String name;
    @NonNull
    String email;
    @NonNull
    String companyAddress;
    @NonNull
    String phone;
}
