package com.project.exhibitions.controller;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ITextsPaths;
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
import java.util.regex.Pattern;

@AllArgsConstructor

@Slf4j
@RestController
@RequestMapping("/")
public class RegistrationFormController {

    private final UserService userService;
    //private final View view = new View();

    @PostMapping("/registration")
    public ModelAndView processRegistration(HttpServletRequest request, Model model, Authentication authentication) {
        Configurator config = new Configurator();
        config.basicConfiguration(model, authentication, View.view);

        UserDTO newUser = new UserDTO(request.getParameter("email"),
                request.getParameter("username"), request.getParameter("password"));

        try {
            if ( ! (Pattern.compile(View.view.getBundleText(ITextsPaths.EMAIL_REGEX)).matcher(newUser.getEmail()).matches()
            && Pattern.compile(View.view.getBundleText(ITextsPaths.USERNAME_REGEX)).matcher(newUser.getUsername()).matches()
            && Pattern.compile(View.view.getBundleText(ITextsPaths.PASSWORD_REGEX)).matcher(newUser.getPassword()).matches())) {
                throw new IllegalArgumentException("Given data is invalid");
            }
            userService.saveNewUser(newUser);
            log.info("New user was saved");
            model.addAttribute("invalid", "You've successfully registered!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("invalid", View.view.getBundleText(ITextsPaths.INVALID_DATA));
            log.info("Error saving new user");
        } catch (DataIntegrityViolationException exc) {
            model.addAttribute("invalid", "Your email is already used! Use another one.");
            log.info("Error saving new user");
        }
        return new ModelAndView("registrationResult");
    }
}
