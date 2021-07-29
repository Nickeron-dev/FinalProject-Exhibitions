package ua.project.exhibition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.project.exhibition.view.TextConstantsPaths;
import ua.project.exhibition.view.View;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Controller
public class PageController {
    @GetMapping("/")
    public String home(@ModelAttribute String message, Model model) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("login", View.bundle.getString(TextConstantsPaths.LOGIN_BUTTON));
        attributes.put("register", View.bundle.getString(TextConstantsPaths.REGISTER_BUTTON));
        model.addAllAttributes(attributes);
        return "index";
    }

    @GetMapping("/ua")
    public String homeUA(@ModelAttribute String message, Model model) {
        View.changeLocale(Optional.of(new Locale("ua", "UA")));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("login", View.bundle.getString(TextConstantsPaths.LOGIN_BUTTON));
        attributes.put("register", View.bundle.getString(TextConstantsPaths.REGISTER_BUTTON));
        model.addAllAttributes(attributes);
        return "index";
    }

    @GetMapping("/en")
    public String homeEN(@ModelAttribute String message, Model model) {
        View.changeLocale(Optional.of(new Locale("en")));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("login", View.bundle.getString(TextConstantsPaths.LOGIN_BUTTON));
        attributes.put("register", View.bundle.getString(TextConstantsPaths.REGISTER_BUTTON));
        model.addAllAttributes(attributes);
        return "index";
    }

}
