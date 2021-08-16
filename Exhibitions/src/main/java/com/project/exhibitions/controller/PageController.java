package com.project.exhibitions.controller;

import com.project.exhibitions.entity.Role;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ILocaleNames;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExhibitionService exhibitionService;

    private final View view = new View();

    @GetMapping("/")
    public String main(Model model, Authentication authentication) {
        model.addAttribute("register", view.getBundleText(ITextsPaths.REGISTER_BUTTON));
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN_BUTTON));
        model.addAttribute("listExhibitions", exhibitionService.allExhibitions());
        try {
            model.addAttribute("isAdmin", authentication.getAuthorities().contains(Role.ADMIN));
        } catch(NullPointerException exc) {

        }

        return "home";
    }

    @PostMapping(value = "/buy")
    public String buyTicket(@RequestParam Integer id) {
        //System.out.println(Integer.parseInt(id));
        System.out.println(id);
        try {
            //System.out.println(Integer.parseInt(id));
        } catch (Exception exc) {
            System.out.println("Err");
        }
        return "hello";
    }
    // TODO add @postmapping method and to check add just sout(it must output id of pressed button 'buy'
    @GetMapping("/home")
    public void home(Model model, Authentication authentication) {
        main(model, authentication);
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

    @GetMapping("/addExhibition")
    public String addExhibition() {
        return "addExhibition";
    }

}
