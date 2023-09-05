package com.jobs.app.controller;

import com.jobs.app.dto.LoginDTO;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import java.util.Objects;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    private String login(
        @ModelAttribute("loginDTO") LoginDTO loginDTO,
        Model model
    ) {
        if (Objects.isNull(loginDTO)
            || StringUtils.isEmptyOrWhitespace(loginDTO.getUsername())
            || StringUtils.isEmptyOrWhitespace(loginDTO.getPassword())
        ) {
            model.addAttribute("loginDTO", loginDTO);
            model.addAttribute("isCredsInvalid", true);
            return "index";
        }

        UserDTO userDTO = loginService.login(loginDTO);

        if (Objects.isNull(userDTO)) {
            model.addAttribute("loginDTO", loginDTO);
            model.addAttribute("isCredsInvalid", true);
            return "index";
        }

        model.addAttribute("user", userDTO);
        return "redirect:/dashboard/" + userDTO.getId();
    }

}
