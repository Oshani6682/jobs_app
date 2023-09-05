package com.jobs.app.controller;

import com.jobs.app.dto.LoginDTO;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = {"/", "/logout"})
    private String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("isCredsInvalid", false);
        return "index";
    }

    @RequestMapping(value = "/dashboard/{userId}")
    private String dashboardView(
        @PathVariable Integer userId,
        Model model
    ) {
        model.addAttribute("userId", userId);
        model.addAttribute("appointments", appointmentService.findAppointments(userId));
        return "user-dashboard";
    }

}
