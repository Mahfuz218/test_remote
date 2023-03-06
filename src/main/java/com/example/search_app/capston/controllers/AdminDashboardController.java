package com.example.search_app.capston.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {
    @GetMapping({"/", "/dashboard", "/index"})
    public String dashboard(Model model) {
        return "admin_templates/job/job-filter";
    }
}
