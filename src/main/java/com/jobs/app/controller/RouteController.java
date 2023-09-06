package com.jobs.app.controller;

import com.jobs.app.domain.User;
import com.jobs.app.dto.CreateUserDTO;
import com.jobs.app.dto.LoginDTO;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.enums.UserRole;
import com.jobs.app.repository.UserRepository;
import com.jobs.app.service.AppointmentService;
import com.jobs.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RouteController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/logout"})
    private String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("isCredsInvalid", false);
        return "index";
    }

    @RequestMapping(value = "/signup")
    private String registration(Model model) {
        model.addAttribute("registerDTO", new CreateUserDTO());
        model.addAttribute("isError", false);
        model.addAttribute("errorMsg", "");
        return "signup";
    }

    @RequestMapping(value = "/dashboard/{userId}")
    private String dashboardView(
        @PathVariable Integer userId,
        Model model
    ) {
        String result = getBase(model, userId);
        if (Objects.nonNull(result)) {
            return result;
        }

        model.addAttribute("appointments", appointmentService.findAppointments(userId));
        return "user-dashboard";
    }

    @RequestMapping(value = "/consultants-view/{userId}")
    private String consultantView(
        @PathVariable Integer userId,
        Model model
    ) {
        String result = getBase(model, userId);
        if (Objects.nonNull(result)) {
            return result;
        }

        model.addAttribute("consultants", userService.findAllConsultants());
        return "consultants-view";
    }

    @PostMapping(value = "/create-user")
    private String createJobSeeker(
        @ModelAttribute("registerDTO") CreateUserDTO createUserDTO,
        Model model
    ) {
        boolean isError = false;
        String errorMessage = "";

        if (StringUtils.isEmptyOrWhitespace(createUserDTO.getUsername())) {
            isError = true;
            errorMessage = "Username cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getFirstName())) {
            isError = true;
            errorMessage = "First name cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getLastName())) {
            isError = true;
            errorMessage = "Last name cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getAddress())) {
            isError = true;
            errorMessage = "Address cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getEmail())) {
            isError = true;
            errorMessage = "Email cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getPassword())) {
            isError = true;
            errorMessage = "Password cannot be empty";
        } else if (!createUserDTO.getEmail().matches("^[a-zA-Z0-9]+[a-zA-Z0-9._]+@[a-zA-Z.]+\\.[a-zA-Z]+$")) {
            isError = true;
            errorMessage = "Email address is invalid";
        } else if (Objects.nonNull(userRepository.findUserByUsername(createUserDTO.getUsername()))) {
            isError = true;
            errorMessage = "Username already taken, try another one";
        }

        if (isError) {
            model.addAttribute("registerDTO", createUserDTO);
            model.addAttribute("isError", isError);
            model.addAttribute("errorMsg", errorMessage);
            return "signup";
        }

        userService.saveJobSeeker(createUserDTO);
        return "redirect:/";
    }

    private String getBase(Model model, Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return "redirect:/";
        }

        User userData = user.get();
        setPermissions(model, userData);

        model.addAttribute("userId", userId);
        model.addAttribute("user", new UserDTO(userData, true));
        return null;
    }

    private void setPermissions(Model model, User user) {
        Map<String, Boolean> permissions = new HashMap<>();
        permissions.put("hasConsultantView", user.userRole != UserRole.CONSULTANT);
        model.addAttribute("permissions", permissions);
    }

}
