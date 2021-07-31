package com.project.exhibitions.controller;

import com.project.exhibitions.view.ILocaleNames;
import com.project.exhibitions.view.View;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;
import java.util.Optional;

@Controller
public class PageController {

    private View view = new View();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("register", view.getBundleText("title.button.register"));
        model.addAttribute("login", view.getBundleText("title.button.login"));
        return "welcome";
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

}
