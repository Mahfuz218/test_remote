package com.example.search_app.capston.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {
    @GetMapping({"/", "/home", "/index"})
    public String dashboard(Model model) {
        return "redirect:letter";
    }
}
