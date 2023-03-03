package com.example.search_app.capston.controllers.controller;

import com.ncms.springmvc.model.AddUserModel;
import com.ncms.springmvc.model.ChangePasswordModel;
import com.ncms.springmvc.repository.RoleRepository;
import com.ncms.springmvc.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;
    private final RoleRepository roleRepository;

    @GetMapping("/userList")
    public String getUserList(Model model) {
        model.addAttribute("users", usersService.getAllUsers());
        return "admin_templates/letter/user-list";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("roleList", roleRepository.findAll());
        model.addAttribute("addUserModel", new AddUserModel());
        return "admin_templates/letter/user-add";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute @Valid AddUserModel addUserModel,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:addUser";
        }
        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "User added successfully.");
            usersService.createNewUser(addUserModel);
            return "redirect:userList";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add User."+e.getMessage());
            return "redirect:addUser";
        }
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model, @RequestParam String userName) {
        model.addAttribute("userModel", usersService.getUserByName(userName));
        model.addAttribute("changePasswordModel", new ChangePasswordModel());
        return "admin_templates/letter/user-profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute @Valid ChangePasswordModel changePasswordModel,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          @RequestParam String userName) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:userProfile?userName="+userName;
        }
        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Password changed successfully.");
            usersService.changePassword(changePasswordModel, userName);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to change password."+e.getMessage());
        }
        return "redirect:userProfile?userName="+userName;
    }

    @GetMapping("/changeUserStatus")
    public String changeUserStatus(@RequestParam long userId,
                                   RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "User status changes successfully");
            usersService.changeUserStatus(userId);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to change user status."+e.getMessage());
        }
        return "redirect:userList";
    }

}
