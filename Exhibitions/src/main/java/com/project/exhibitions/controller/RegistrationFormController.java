package com.project.exhibitions.controller;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor

@Slf4j
@RestController
@RequestMapping("/")
public class RegistrationFormController {

    private final UserService userService;
    private final View view = new View();

    @PostMapping("/registration")
    public ModelAndView processRegistration(HttpServletRequest request, Model model, Authentication authentication) {
        Configurator config = new Configurator();
        config.basicConfiguration(model, authentication, view);

        UserDTO newUser = new UserDTO(request.getParameter("email"),
                request.getParameter("username"), request.getParameter("password"));

        try {
            userService.saveNewUser(newUser);
            log.info("New user was saved");
            model.addAttribute("usedEmail", "You've successfully registered!");
        } catch (DataIntegrityViolationException exc) {
            model.addAttribute("usedEmail", "Your email is already used! Use another one.");
            log.info("Error saving new user");
        }
        return new ModelAndView("registrationResult");
    }
}
