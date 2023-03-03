package com.example.search_app.capston.controllers.controller;


import com.ncms.springmvc.model.ConcernedPartyModel;
import com.ncms.springmvc.service.ConcernedPartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ConcernedPartiesController {

    private final ConcernedPartyService concernedPartyService;

    @GetMapping("/concernedParties")
    public String getConcernedPartyList(Model model) {
        model.addAttribute("cParties", concernedPartyService.getConcernedPartyList());
        model.addAttribute("concernedPartyModel", new ConcernedPartyModel());
        return "admin_templates/letter/concerned-party-list";
    }

    @PostMapping("/addConcernedParty")
    public String addConcernedParty(@ModelAttribute ConcernedPartyModel concernedPartyModel,
                                    RedirectAttributes redirectAttributes,
                                    BindingResult result) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/concernedParties";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Concerned party added successfully.");
            concernedPartyService.creatConcernedParty(concernedPartyModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add Concerned party."+e.getMessage());
        }
        return "redirect:/concernedParties";
    }

    @PostMapping("/updateConcernedParty")
    public String updateConcernedParty(@ModelAttribute ConcernedPartyModel concernedPartyModel,
                                    RedirectAttributes redirectAttributes,
                                    BindingResult result) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/concernedParties";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Concerned party updated successfully.");
            concernedPartyService.updateConcernedParty(concernedPartyModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to update Concerned party."+e.getMessage());
        }
        return "redirect:/concernedParties";
    }

    @GetMapping("/cParty/delete")
    public String deleteCabinet(@RequestParam long cParty, RedirectAttributes redirectAttributes) {
        try {
            concernedPartyService.deleteConcernedParty(cParty);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Concerned Party Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete Concerned Party,"+e.getMessage());
        }
        return "redirect:/concernedParties";
    }



}
