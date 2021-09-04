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

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor

@Slf4j
@RestController
@RequestMapping("/")
public class RegistrationFormController {

    private final UserService userService;

    /**
     * Processing registration method
     * @param request This object is used to get inputted parameter
     * @param model To add attribute to show text
     * @param authentication To get user's role
     * @return ModelAndView(redirect) to result of the operation
     */
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
            model.addAttribute("invalid", View.view.getBundleText(ITextsPaths.REGISTER_SUCCESS));
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
