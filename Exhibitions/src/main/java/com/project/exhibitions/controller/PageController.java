package com.project.exhibitions.controller;

import com.project.exhibitions.entity.User;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ILocaleNames;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private UserService userService = new UserService();

    private final View view = new View();

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("register", view.getBundleText(ITextsPaths.REGISTER_BUTTON));
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN_BUTTON));
        return "home";
    }

    @GetMapping("/home")
    public void home(Model model) {
        main(model);
    }

    @PostMapping(value="/change-language", params="ukr")
    public RedirectView ukr() {
        view.changeLocale(Optional.of(new Locale(ILocaleNames.UKR_LANGUAGE, ILocaleNames.UKR_COUNTRY)));
        return new RedirectView("/");
    }

    @PostMapping(value="/change-language", params="eng")
    public RedirectView eng() {
        view.changeLocale(Optional.of(new Locale(ILocaleNames.DEFAULT_LANGUAGE)));
        return new RedirectView("/");
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(HttpServletRequest request) throws Exception {
        User newUser = new User(request.getParameter("email"),
                request.getParameter("username"), request.getParameter("password"));
        userService.saveNewUser(newUser);
        return "successfulRegistration";
    }

}
