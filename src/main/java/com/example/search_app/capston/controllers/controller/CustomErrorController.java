package com.example.search_app.capston.controllers.controller;

import com.ncms.springmvc.model.ErrorModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        ErrorModel errorModel = new ErrorModel();
        errorModel.setMessage("Something went wrong, Please try again later.");
        errorModel.setErrorCode("Unknown Error");
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            errorModel.setErrorCode(statusCode + "");
            errorModel.setMessage("Something went wrong, Please try again later.");
        }
        model.addAttribute("errorModel", errorModel);
        return "admin_templates/error/admin-error.html";

    }
}
