package com.example.search_app.capston.controllers.controller;

import com.ncms.springmvc.model.RackModel;
import com.ncms.springmvc.service.RackService;
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

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RackController {

    private final RackService rackService;


    @GetMapping("/rackList")
    public String getRackListPage(Model model) {
        model.addAttribute("racks", rackService.getRackList());
        return "admin_templates/letter/rack-list";
    }

    @PostMapping("/rack/add")
    public String addRack(@ModelAttribute @Valid RackModel rackModel,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.info("Failed to validate add Rack form");
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/rackList";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Rack added successfully.");
            rackService.createRack(rackModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add rack.");
        }
        return "redirect:/rackList";
    }


    @PostMapping("/rack/update")
    public String updateRack(@ModelAttribute @Valid RackModel rackModel,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.info("Failed to validate update Rack form");
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/rackList";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Rack Updated successfully.");
            rackService.updateRack(rackModel, rackModel.getId());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to update rack."+ e.getMessage());
        }
        return "redirect:/rackList";
    }

    @GetMapping("/rack/delete")
    public String deleteRack(@RequestParam long id, RedirectAttributes redirectAttributes) {
        try {
            rackService.deleteRack(id);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Rack Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete Rack,"+e.getMessage());
        }
        return "redirect:/rackList";
    }

}
