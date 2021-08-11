package com.project.exhibitions.controller;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.entity.User;
import com.project.exhibitions.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor

@RestController
@RequestMapping("/")
public class RegistrationFormController {

    private final UserService userService;

    @PostMapping("/registration")
    public ModelAndView processRegistration(HttpServletRequest request, Model model) {
        UserDTO newUser = new UserDTO(request.getParameter("email"),
                request.getParameter("username"), request.getParameter("password"));

        try {
            userService.saveNewUser(newUser);
            model.addAttribute("usedEmail", "You've successfully registered!");
        } catch (DataIntegrityViolationException exc) {
            model.addAttribute("usedEmail", "Your email is already used! Use another one.");
        }
        return new ModelAndView("successfulRegistration");
    }
}
