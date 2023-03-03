package com.example.search_app.capston.services.impl;
import com.example.search_app.capston.dto.AddUserModel;
import com.example.search_app.capston.dto.ChangePasswordModel;
import com.example.search_app.capston.dto.UserModel;
import com.example.search_app.capston.models.Role;
import com.example.search_app.capston.models.Users;
import com.example.search_app.capston.repositories.RoleRepository;
import com.example.search_app.capston.repositories.UsersRepository;
import com.example.search_app.capston.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Touhid Hossain
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @Override
    public Users getUsersByUserName(String username) {
        return usersRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found with username: "+username)
                );
    }

    @Override
    public List<UserModel> getAllUsers() {
        return usersRepository.findAll()
                .stream().map(users -> {
                    UserModel userModel = new UserModel();
                    userModel.setId(users.getId());
                    userModel.setUserName(users.getUsername());
                    userModel.setEmail(users.getEmail());
                    userModel.setStatus(users.isAccountNonLocked() ? 1 : 0);
                    userModel.setRole(new ArrayList<>(users.getRoleList()).get(0).getName());
                    return userModel;
                }).collect(Collectors.toList());
    }

    @Override
    public void changeUserStatus(long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id:" + userId));
        if (users.isAccountNonExpired()) {
            users.setAccountNonExpired(false);
            users.setAccountNonLocked(false);
            users.setEnabled(false);
        } else {
            users.setAccountNonExpired(true);
            users.setAccountNonLocked(true);
            users.setEnabled(true);
        }
        usersRepository.save(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createNewUser(AddUserModel addUserModel) {

        Role role = roleRepository.findById(addUserModel.getRole().longValue())
                .orElseThrow(() -> new RuntimeException("Role not found with id:" + addUserModel.getRole()));

        Users users = new Users();
        users.setEmail(addUserModel.getEmail());
        users.setUsername(addUserModel.getUserName());
        users.setPassword(new BCryptPasswordEncoder().encode(addUserModel.getPassword()));
        users.setRoleList(Set.of(role));
        users.setEnabled(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setCredentialsNonExpired(true);

        usersRepository.save(users);

    }

    @Override
    public UserModel getUserByName(String userName) {
        Users users = getUsersByUserName(userName);
        UserModel userModel = new UserModel();
        userModel.setUserName(users.getUsername());
        userModel.setRole(new ArrayList<>(users.getRoleList()).get(0).getName());
        userModel.setEmail(users.getEmail());
        return userModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(ChangePasswordModel changePasswordModel, String userName) {
        Users users = getUsersByUserName(userName);

        if (!new BCryptPasswordEncoder().matches(changePasswordModel.getOldPassword(), users.getPassword())) {
            throw new RuntimeException("Old password is not correct.");
        }

        users.setPassword(new BCryptPasswordEncoder().encode(changePasswordModel.getPassword()));

        usersRepository.save(users);
    }
}
