package com.example.search_app.capston.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobController {


    @GetMapping("/jobs")
    public String getJobList() {
        return "jobList";
    }

}
