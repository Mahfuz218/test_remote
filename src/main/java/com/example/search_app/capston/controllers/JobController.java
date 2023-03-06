package com.example.search_app.capston.controllers;


import com.example.search_app.capston.dto.AddCompanyDto;
import com.example.search_app.capston.dto.AddJobDto;
import com.example.search_app.capston.services.CompanyService;
import com.example.search_app.capston.services.JobService;
import com.example.search_app.capston.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;
    private final CompanyService companyService;
    private final SkillService skillService;


    @GetMapping
    public String getJobList(Model model) {
        model.addAttribute("jobList", jobService.getAllJob());
        return "admin_templates/job/job-list";
    }

    @GetMapping("/filter-page")
    public String getUserJobs(Model model) {
        model.addAttribute("jobList", jobService.getAllJob());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("companyList", companyService.getAllCompany());
        return "admin_templates/job/job-filter";
    }

    @GetMapping("/add")
    public String addJob(Model model) {
        model.addAttribute("addJobDto", new AddJobDto());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("companyList", companyService.getAllCompany());
        return "admin_templates/job/job-add";
    }

    @PostMapping("/add")
    public String addJob(@ModelAttribute AddJobDto addJobDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/job/add";
        }

        try {
            jobService.addOrUpdateJob(addJobDto);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Job added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add Job,"+e.getMessage());
        }

        return "redirect:/job";
    }

}
