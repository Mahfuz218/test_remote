package com.example.search_app.capston.services;


import com.example.search_app.capston.dto.AddUserModel;
import com.example.search_app.capston.dto.ChangePasswordModel;
import com.example.search_app.capston.dto.UserModel;
import com.example.search_app.capston.models.Users;

import java.util.List;

/**
 * @author Touhid Hossain
 */
public interface UsersService {
    Users getUsersByUserName(String username);

    List<UserModel> getAllUsers();

    void changeUserStatus(long userId);

    void createNewUser(AddUserModel addUserModel);

    UserModel getUserByName(String userName);

    void changePassword(ChangePasswordModel changePasswordModel, String userName);

}
