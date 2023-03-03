package com.example.search_app.capston.controllers.controller;

import com.ncms.springmvc.domain.Letter;
import com.ncms.springmvc.model.LetterModel;
import com.ncms.springmvc.service.ConcernedPartyService;
import com.ncms.springmvc.service.LetterService;
import com.ncms.springmvc.service.RoomService;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;
    private final RoomService roomService;
    private final ConcernedPartyService concernedPartyService;

    @GetMapping("/letter")
    public String addLetter(Model model){
        model.addAttribute("letterModel", new LetterModel());
        model.addAttribute("roomList", roomService.getAllRooms());
        model.addAttribute("cPartyList", concernedPartyService.getConcernedPartyList());
        return "admin_templates/letter/add-letter";
    }

    @PostMapping("/letter")
    public String addLetter(@ModelAttribute LetterModel letterModel,
                            BindingResult result,
                            RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");

            return "redirect:letter";
        }


        try {
            letterService.createLetter(letterModel);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Letter added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add letter,"+e.getMessage());
        }

        return "redirect:letter";
    }

    @GetMapping("/updateLetter")
    public String updateLetter(Model model,
                               @RequestParam long letterId,
                               RedirectAttributes redirectAttributes){
        Optional<Letter> letterOptional = letterService.getById(letterId);
        if (letterOptional.isEmpty()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Letter not found with id:"+ letterId);
            return "redirect:letterList";
        }

        Letter letter = letterOptional.get();
        LetterModel letterModel = new LetterModel();
        letterModel.setId(letterId);
        letterModel.setSubject(letter.getSubject());
        letterModel.setInOut(letter.getInOut());
        letterModel.setLetterConcernedParty(letter.getConcernedParty().getId());
        letterModel.setInternalLetterNumber(letter.getInternalLetterNumber());
        letterModel.setLetterWithReferenceNumber(letter.getLetterWithReferenceNumber());

        letterModel.setLetterNumberInRack(letter.getLetterNumberInRack());
        letterModel.setLetterPdfUrl(letter.getPdfLetterUrl());
        letterModel.setDate(letter.getGDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        letterModel.setHDate(letter.getHDate());
        letterModel.setRoomId(letter.getRoom().getId());
        letterModel.setRoomTitle(letter.getRoom().getTitle());
        letterModel.setCabinetId(letter.getCabinet().getId());
        letterModel.setCabinetTitle(letter.getCabinet().getTitle());
        letterModel.setRackId(letter.getRack().getId());
        letterModel.setRackTitle(letter.getRack().getTitle());

        System.out.println(letterModel);

        model.addAttribute("letterModel", letterModel);
        model.addAttribute("roomList", roomService.getAllRooms());
        model.addAttribute("cPartyList", concernedPartyService.getConcernedPartyList());
        return "admin_templates/letter/edit-letter";
    }

    @PostMapping("/updateLetter")
    public String updateLetter(
                               @ModelAttribute @Valid LetterModel letterModel,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:letter";
        }

        try {
            Optional<Letter> letterOptional = letterService.getById(letterModel.getId());
            if (letterOptional.isEmpty()) {
                redirectAttributes.addAttribute("messageType", "Failed");
                redirectAttributes.addAttribute("class", "bg-danger");
                redirectAttributes.addAttribute("message", "Letter not found with id:"+ letterModel.getId());
                return "redirect:letter";
            }
            letterService.updateLetter(letterOptional.get(), letterModel);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Letter Updated successfully.");
            return "redirect:letter";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed, "+e.getMessage());
            return "redirect:letter";
        }
    }

    @GetMapping("/deleteLetter")
    public String deleteLetter(@RequestParam long letterId, RedirectAttributes redirectAttributes) {
        try {
            letterService.deleteLetter(letterId);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Letter Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete letter,"+e.getMessage());
        }
        return "redirect:letterList";
    }


    @GetMapping("/letterList")
    public String getLetters(Model model) {
        model.addAttribute("letters", letterService.getAllLetters());
        model.addAttribute("cParty", concernedPartyService.getConcernedPartyList());
        return "admin_templates/letter/letter-list";
    }


}
