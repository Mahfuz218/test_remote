package com.example.search_app.capston.controllers.controller;

import com.ncms.springmvc.domain.Cabinet;
import com.ncms.springmvc.domain.Room;
import com.ncms.springmvc.model.CabinetModel;
import com.ncms.springmvc.model.RoomModel;
import com.ncms.springmvc.service.CabinetService;
import com.ncms.springmvc.service.RoomService;
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
public class RoomController {

    private final CabinetService cabinetService;
    private final RoomService roomService;


    @GetMapping("/roomList")
    public String getRoomListPage(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin_templates/letter/room-list";
    }

    @GetMapping("/room/add")
    public String addRoom(Model model) {
        model.addAttribute("roomModel", new RoomModel());
        model.addAttribute("cabinetList", cabinetService.getNonAssignedCabinetList());
        return "admin_templates/letter/room-add";
    }

    @GetMapping("/room/update")
    public String updateRoom(Model model, @RequestParam long roomId,
                                RedirectAttributes redirectAttributes) {
        Optional<Room> roomOptional = roomService.getById(roomId);
        if (roomOptional.isEmpty()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Room not found with id:"+ roomId);
            return "redirect:/roomList";
        }
        Room room = roomOptional.get();
        List<Long> cabinetList = room.getCabinets().stream().map(Cabinet::getId).collect(Collectors.toList());
        RoomModel roomModel = new RoomModel();
        roomModel.setId(room.getId());
        roomModel.setTitle(room.getTitle());
        roomModel.setDescription(room.getDescription());
        roomModel.setCabinets(cabinetList);

        List<CabinetModel> cabinetModels = cabinetService.getNonAssignedCabinetList()
                .stream().map(p -> {
                    CabinetModel cabinetModel = new CabinetModel();
                    cabinetModel.setCabinetId(p.getId());
                    cabinetModel.setTitle(p.getTitle());
                    cabinetModel.setLabel(p.getLabel());
                    return cabinetModel;
                }).collect(Collectors.toList());
        List<CabinetModel> oldCabinets = room.getCabinets().stream().map(cabinet -> {
            CabinetModel cabinetModel = new CabinetModel();
            cabinetModel.setCabinetId(cabinet.getId());
            cabinetModel.setTitle(cabinet.getTitle());
            cabinetModel.setLabel(cabinet.getLabel());
            return cabinetModel;
        }).collect(Collectors.toList());


        cabinetModels.addAll(oldCabinets);

        System.out.println(cabinetModels.size());

        model.addAttribute("roomModel", roomModel);
        model.addAttribute("cabinetList", cabinetModels);
        return "admin_templates/letter/room-edit";
    }

    @PostMapping("/room/add")
    public String addRoom(@ModelAttribute @Valid RoomModel roomModel,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/roomList";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Room added successfully.");
            roomService.createRoom(roomModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add Room."+e.getMessage());
        }
        return "redirect:/roomList";
    }


    @PostMapping("/room/update")
    public String updateRoom(@ModelAttribute @Valid RoomModel roomModel,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.info("Failed to validate update Cabinet form");
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/roomList";
        }

        try {
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Room Updated successfully.");
            roomService.updateRoom(roomModel);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to update Room."+ e.getMessage());
        }
        return "redirect:/roomList";
    }

    @GetMapping("/room/delete")
    public String deleteRoom(@RequestParam long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Room Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete Room,"+e.getMessage());
        }
        return "redirect:/roomList";
    }


}
