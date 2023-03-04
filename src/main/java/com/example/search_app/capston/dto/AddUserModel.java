package com.example.search_app.capston.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


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
