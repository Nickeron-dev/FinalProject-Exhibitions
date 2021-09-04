package com.project.exhibitions.controller;

import com.project.exhibitions.view.View;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Illia Koshkin
 */
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * This method fills error page
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return String error page
     */
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(Model model, Authentication authentication) {
        Configurator configurator = new Configurator();
        configurator.basicConfiguration(model, authentication, View.view);
        return "error";
    }
}
