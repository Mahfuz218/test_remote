package com.example.search_app.capston.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordModel {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

}
