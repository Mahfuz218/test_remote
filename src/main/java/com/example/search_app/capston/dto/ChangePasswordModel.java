package com.example.search_app.capston.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class ChangePasswordModel {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

}
