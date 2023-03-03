package com.example.search_app.capston.controllers.controller;

import com.ncms.springmvc.domain.Cabinet;
import com.ncms.springmvc.domain.Rack;
import com.ncms.springmvc.model.CabinetModel;
import com.ncms.springmvc.model.RackModel;
import com.ncms.springmvc.service.CabinetService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CabinetController {

    private final CabinetService cabinetService;
    private final RackService rackService;


    @GetMapping("/cabinetList")
    public String getCabinetListPage(Model model) {
        model.addAttribute("cabinets", cabinetService.getCabinetList());
        return "admin_templates/letter/cabinet-list";
    }

    @GetMapping("/cabinet/add")
    public String addCabinet(Model model) {
        model.addAttribute("cabinetModel", new CabinetModel());
        model.addAttribute("rackList", rackService.getAllNonAssignedRacks());
        return "admin_templates/letter/cabinet-add";
    }

    @GetMapping("/cabinet/update")
    public String updateCabinet(Model model, @RequestParam long cabinetId,
                                RedirectAttributes redirectAttributes) {
        Optional<Cabinet> cabinetOptional = cabinetService.getById(cabinetId);
        if (cabinetOptional.isEmpty()) {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Cabinet not found with id:"+ cabinetId);
            return "redirect:/cabinetList";
        }
        Cabinet cabinet = cabinetOptional.get();
        List<Long> rackList = cabinet.getRacks().stream().map(Rack::getId).collect(Collectors.toList());
        CabinetModel cabinetModel = new CabinetModel();
        cabinetModel.setCabinetId(cabinet.getId());
        cabinetModel.setTitle(cabinet.getTitle());
        cabinetModel.setLabel(cabinet.getLabel());
        cabinetModel.setNoOfRack(rackList.size());
        cabinetModel.setRackList(rackList);

        List<RackModel> rackModels = rackService.getAllNonAssignedRacks()
                .stream().map(rackProjection -> {
                    RackModel rackModel = new RackModel();
                    rackModel.setId(rackProjection.getId());
                    rackModel.setTitle(rackProjection.getTitle());
                    rackModel.setDescription(rackProjection.getDescription());
                    return rackModel;
                }).collect(Collectors.toList());
        List<RackModel> oldRacks = cabinet.getRacks().stream().map(rack -> {
            RackModel rackModel = new RackModel();
            rackModel.setId(rack.getId());
            rackModel.setTitle(rack.getTitle());
            rackModel.setDescription(rack.getDescription());
            return rackModel;
        }).collect(Collectors.toList());


        rackModels.addAll(oldRacks);

        System.out.println(rackModels.size());

        model.addAttribute("cabinetModel", cabinetModel);
        model.addAttribute("rackList", rackModels);
        return "admin_templates/letter/cabinet-edit";
    }

    @PostMapping("/cabinet/add")
    public String addCabinet(@ModelAttribute @Valid CabinetModel cabinetModel,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/cabinetList";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Cabinet added successfully.");
            cabinetService.createCabinet(cabinetModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add Cabinet."+e.getMessage());
        }
        return "redirect:/cabinetList";
    }


    @PostMapping("/cabinet/update")
    public String updateCabinet(@ModelAttribute @Valid CabinetModel cabinetModel,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.info("Failed to validate update Cabinet form");
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/cabinetList";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Cabinet Updated successfully.");
            cabinetService.updateCabinet(cabinetModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to update Cabinet."+ e.getMessage());
        }
        return "redirect:/cabinetList";
    }


    @GetMapping("/cabinet/delete")
    public String deleteCabinet(@RequestParam long id, RedirectAttributes redirectAttributes) {
        try {
            cabinetService.deleteCabinet(id);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Cabinet Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete Cabinet,"+e.getMessage());
        }
        return "redirect:/cabinetList";
    }

}
