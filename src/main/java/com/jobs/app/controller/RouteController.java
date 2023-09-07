package com.jobs.app.controller;

import com.jobs.app.domain.User;
import com.jobs.app.dto.*;
import com.jobs.app.enums.UserRole;
import com.jobs.app.repository.ConsultantAvailabilityRepository;
import com.jobs.app.repository.ConsultantRepository;
import com.jobs.app.repository.UserRepository;
import com.jobs.app.service.AppointmentService;
import com.jobs.app.service.CountryService;
import com.jobs.app.service.SectorService;
import com.jobs.app.service.UserService;
import com.jobs.app.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private ConsultantAvailabilityRepository availabilityRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private SectorService sectorService;

    @RequestMapping(value = {"/", "/logout"})
    private String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("isCredsInvalid", false);
        return "index";
    }

    @RequestMapping(value = "/signup")
    private String registration(Model model) {
        includeError(model, false, "");
        model.addAttribute("registerDTO", new CreateUserDTO());
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

    @RequestMapping(value = "/book-appointment")
    private String getBookAppointmentView(
        @RequestParam Integer jobSeeker,
        @RequestParam Integer consultant,
        Model model
    ) {
        String result = getBase(model, jobSeeker);
        if (Objects.nonNull(result)) {
            return result;
        }

        model.addAttribute("consultant", userService.findConsultantById(consultant, HttpStatus.NOT_FOUND));
        model.addAttribute("appointmentDTO", new CreateAppointmentDTO());
        model.addAttribute("consultantAvailabilities", availabilityRepository.findAllByConsultantAndDayExcludingCode(consultant));
        return "book-appointment-view";
    }

    @RequestMapping(value = "/cancel-appointment")
    private String cancelAppointment(
        @RequestParam Integer user,
        @RequestParam Integer appointment,
        Model model
    ) {
        String result = getBase(model, user);
        if (Objects.nonNull(result)) {
            return result;
        }

        appointmentService.cancelAppointment(model, user, appointment);
        model.addAttribute("appointments", appointmentService.findAppointments(user));
        return "user-dashboard";
    }

    @RequestMapping(value = "/register-user")
    private String getRegisterUserView(
        @RequestParam Integer user,
        @RequestParam String userRole,
        Model model
    ) {
        String result = getBase(model, user);
        if (Objects.nonNull(result)) {
            return result;
        }

        UserRole matchRole = Utils.findMatchingUserRole(userRole);
        if (Objects.isNull(matchRole) || matchRole == UserRole.JOB_SEEKER) {
            return "redirect:/dashboard/" + user;
        }

        model.addAttribute("userDisplayRole", Utils.capitalizeEachLetter(matchRole.name()));
        model.addAttribute("userRole", userRole);
        model.addAttribute("registerDTO", new CreateConsultantDTO());

        if (matchRole == UserRole.CONSULTANT) {
            model.addAttribute("countries", countryService.findCountries());
            model.addAttribute("sectors", sectorService.findSectors());
        }

        return "create-user";
    }

    @PostMapping(value = "/confirm-register")
    private String confirmRegister(
        @RequestParam Integer user,
        @RequestParam String userRole,
        @ModelAttribute("registerDTO") CreateConsultantDTO createUserDTO,
        Model model
    ) {
        String result = getBase(model, user);
        if (Objects.nonNull(result)) {
            return result;
        }

        UserRole matchRole = Utils.findMatchingUserRole(userRole);
        if (Objects.isNull(matchRole) || matchRole == UserRole.JOB_SEEKER) {
            return "redirect:/dashboard/" + user;
        }

        boolean isError = true;
        String errorMessage = "";

        if (StringUtils.isEmptyOrWhitespace(createUserDTO.getUsername())) {
            errorMessage = "Username cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getFirstName())) {
            errorMessage = "First name cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getLastName())) {
            errorMessage = "Last name cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getAddress())) {
            errorMessage = "Address cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getEmail())) {
            errorMessage = "Email cannot be empty";
        } else if (StringUtils.isEmptyOrWhitespace(createUserDTO.getPassword())) {
            errorMessage = "Password cannot be empty";
        } else if (!createUserDTO.getEmail().matches("^[a-zA-Z0-9]+[a-zA-Z0-9._]+@[a-zA-Z.]+\\.[a-zA-Z]+$")) {
            errorMessage = "Email address is invalid";
        } else if (Objects.nonNull(userRepository.findUserByUsername(createUserDTO.getUsername()))) {
            errorMessage = "Username already taken, try another one";
        } else if (matchRole == UserRole.CONSULTANT
            && createUserDTO.getCountry() <= 0
        ) {
            errorMessage = "Consultant country not selected";
        }  else if (matchRole == UserRole.CONSULTANT
            && createUserDTO.getSector() <= 0
        ) {
            errorMessage = "Consultant sector not selected";
        } else {
            isError = false;
        }

        if (isError) {
            includeError(model, true, errorMessage);
            model.addAttribute("userDisplayRole", Utils.capitalizeEachLetter(matchRole.name()));
            model.addAttribute("userRole", userRole);
            model.addAttribute("registerDTO", createUserDTO);

            if (matchRole == UserRole.CONSULTANT) {
                model.addAttribute("countries", countryService.findCountries());
                model.addAttribute("sectors", sectorService.findSectors());
            }

            return "create-user";
        }

        if (matchRole == UserRole.CONSULTANT) {
            userService.saveConsultant(createUserDTO);
            return "redirect:/consultants-view/" + user;
        } else {
            userService.saveUser(createUserDTO, matchRole);
            return "redirect:/dashboard/" + user;
        }
    }

    @PostMapping(value = "/confirm-appointment")
    private String confirmAppointment(
        @RequestParam Integer jobSeeker,
        @RequestParam Integer consultant,
        @ModelAttribute("appointmentDTO") CreateAppointmentDTO appointmentDTO,
        Model model
    ) {
        String result = getBase(model, jobSeeker);
        if (Objects.nonNull(result)) {
            return result;
        }

        boolean isError = true;
        String errorMsg = "";

        if (StringUtils.isEmptyOrWhitespace(appointmentDTO.getAppointmentDate())) {
            errorMsg = "Appointment date is not selected";
        } else if (StringUtils.isEmptyOrWhitespace(appointmentDTO.getFromTime())) {
            errorMsg = "Appointment from time not set";
        } else if (StringUtils.isEmptyOrWhitespace(appointmentDTO.getToTime())) {
            errorMsg = "Appointment to time not set";
        } else if (!appointmentDTO.getAppointmentDate().matches("^(202[3-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$")) {
            errorMsg = "Appointment date is invalid. Date format should be in YYYY-MM-DD format";
        } else if (!appointmentDTO.getFromTime().matches("^([0-1][0-9]|2[0-4]):([0-5][0-9])$")) {
            errorMsg = "Appointment start time is invalid. Time format should be in 24 hrs (HH:mm)";
        } else if (!appointmentDTO.getToTime().matches("^([0-1][0-9]|2[0-4]):([0-5][0-9])$")) {
            errorMsg = "Appointment end time is invalid. Time format should be in 24 hrs (HH:mm)";
        } else {
            isError = false;
        }

        includeError(model, isError, errorMsg);
        if (isError) {
            model.addAttribute("consultant", userService.findConsultantById(consultant, HttpStatus.NOT_FOUND));
            model.addAttribute("appointmentDTO", appointmentDTO);
            model.addAttribute("consultantAvailabilities", availabilityRepository.findAllByConsultantAndDayExcludingCode(consultant));
            return "book-appointment-view";
        }

        appointmentDTO.setConsultant(consultant);
        appointmentDTO.setJobSeeker(jobSeeker);
        AppointmentDTO appointment = appointmentService.saveAppointment(model, appointmentDTO);

        if (Objects.isNull(appointment)) {
            model.addAttribute("consultant", userService.findConsultantById(consultant, HttpStatus.NOT_FOUND));
            model.addAttribute("appointmentDTO", appointmentDTO);
            model.addAttribute("consultantAvailabilities", availabilityRepository.findAllByConsultantAndDayExcludingCode(consultant));
            return "book-appointment-view";
        }

        return "redirect:/dashboard/" + jobSeeker;
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
        includeError(model, false, "");

        model.addAttribute("userId", userId);
        model.addAttribute("user", new UserDTO(userData, true));
        return null;
    }

    private void setPermissions(Model model, User user) {
        Map<String, Boolean> permissions = new HashMap<>();
        permissions.put("hasConsultantView", user.userRole != UserRole.CONSULTANT);
        permissions.put("canBookAppointment", user.userRole == UserRole.JOB_SEEKER);
        permissions.put("canCancelAppointment", user.userRole == UserRole.JOB_SEEKER || user.userRole == UserRole.CONSULTANT);
        permissions.put("canCreateUsers", user.userRole == UserRole.ADMIN);
        model.addAttribute("permissions", permissions);
    }

    private void includeError(Model model, boolean isError, String errorMsg) {
        model.addAttribute("isError", isError);
        model.addAttribute("errorMsg", errorMsg);
    }

}
