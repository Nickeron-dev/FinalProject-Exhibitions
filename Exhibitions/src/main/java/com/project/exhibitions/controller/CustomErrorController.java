package com.project.exhibitions.controller;

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
        Configurator configurator = new Configurator();
        configurator.basicConfiguration(model, authentication, view);
        return "error";
    }
}
