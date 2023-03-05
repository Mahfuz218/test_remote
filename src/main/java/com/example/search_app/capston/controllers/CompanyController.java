package com.example.search_app.capston.controllers;

import com.example.search_app.capston.dto.AddCompanyDto;
import com.example.search_app.capston.models.Company;
import com.example.search_app.capston.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public String getCompanyList(Model model) {
        model.addAttribute("companyList", companyService.getAllCompany());
        return "admin_templates/company/company-list";
    }

    @GetMapping("/add")
    public String addCompany(Model model) {
        model.addAttribute("addCompanyDto", new AddCompanyDto());
        return "admin_templates/company/company-add";
    }

    @PostMapping("/add")
    public String addCompany(@ModelAttribute AddCompanyDto addCompanyDto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/company/add";
        }

        try {
            companyService.add(addCompanyDto);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Company added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add company,"+e.getMessage());
        }

        return "redirect:/company";
    }


    @GetMapping("/update")
    public String updateCompany(Model model,
                               @RequestParam long companyId,
                               RedirectAttributes redirectAttributes){
        try {
            Company company = companyService.getCompanyById(companyId);

            AddCompanyDto addCompanyDto = new AddCompanyDto();
            //addCompanyDto.setId(companyId);
            addCompanyDto.setCompanyAddress(company.getCompanyAddress());
            addCompanyDto.setEmail(company.getEmail());
            addCompanyDto.setPhone(company.getPhone());
            addCompanyDto.setName(company.getName());

            model.addAttribute("addCompanyDto",addCompanyDto);
            model.addAttribute("companyId", companyId);

            return "admin_templates/company/company-edit";
        } catch (Exception e) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Company not found with id:"+ companyId);
            return "redirect:/company";
        }
    }


    @PostMapping("/update/{companyId}")
    public String updateCompany(
            @ModelAttribute @Valid AddCompanyDto addCompanyDto,
            @PathVariable long companyId,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/company/update";
        }

        try {

            companyService.updateCompany(addCompanyDto, companyId);

            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Company Updated successfully.");
            return "redirect:/company";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed, "+e.getMessage());
            return "redirect:/company";
        }
    }


    @GetMapping("/delete")
    public String deleteCompany(@RequestParam long companyId, RedirectAttributes redirectAttributes) {
        try {
            companyService.deleteCompany(companyId);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Company Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete Company,"+e.getMessage());
        }
        return "redirect:/company";
    }

}
