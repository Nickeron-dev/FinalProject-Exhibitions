package com.project.exhibitions.controller;

import com.project.exhibitions.entity.Role;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    private final View view = new View();

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(Model model, Authentication authentication) {
        model.addAttribute("home", view.getBundleText(ITextsPaths.HOME));
        model.addAttribute("register", view.getBundleText(ITextsPaths.REGISTER));
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN));
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
        return "error";
    }
}
