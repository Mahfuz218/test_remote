package com.example.search_app.capston.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddUserModel {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;
    @NotNull
    private Integer role;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

}
