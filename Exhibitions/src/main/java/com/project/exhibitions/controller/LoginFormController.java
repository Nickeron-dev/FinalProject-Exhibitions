package com.project.exhibitions.controller;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.entity.Role;
import com.project.exhibitions.entity.User;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor

@Slf4j
@RestController
@RequestMapping("/")
public class LoginFormController {

    private final UserService userService;

    @PostMapping("/login")
    public String loginUser(UserDTO user) throws UsernameNotFoundException {
        userService.loadUserByUsername(user.getUsername());
        log.info("User logged in");
        return "login";
    }

}
