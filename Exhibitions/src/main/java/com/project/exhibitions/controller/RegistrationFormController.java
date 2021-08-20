package com.project.exhibitions.controller;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.entity.Role;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
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
    private final View view = new View();

    @PostMapping("/registration")
    public ModelAndView processRegistration(HttpServletRequest request, Model model, Authentication authentication) {
        model.addAttribute("home", view.getBundleText(ITextsPaths.HOME));
        model.addAttribute("register", view.getBundleText(ITextsPaths.REGISTER_HREF));
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN_HREF));
        model.addAttribute("logout", view.getBundleText(ITextsPaths.LOGOUT_HREF));
        try {
            model.addAttribute("isAuthorized", authentication.isAuthenticated());
        } catch(NullPointerException exc) {
            model.addAttribute("isAuthorized", false);
        }
        model.addAttribute("addExhibition", view.getBundleText(ITextsPaths.ADD_EXHIBITION_HREF));
        model.addAttribute("statistics", view.getBundleText(ITextsPaths.STATISTICS_HREF));
        try {
            model.addAttribute("isAdmin", authentication.getAuthorities().contains(Role.ADMIN));
        } catch(NullPointerException exc) {

        }
        UserDTO newUser = new UserDTO(request.getParameter("email"),
                request.getParameter("username"), request.getParameter("password"));

        try {
            userService.saveNewUser(newUser);
            model.addAttribute("usedEmail", "You've successfully registered!");
        } catch (DataIntegrityViolationException exc) {
            model.addAttribute("usedEmail", "Your email is already used! Use another one.");
        }
        return new ModelAndView("registrationResult");
    }
}
