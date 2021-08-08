package com.project.exhibitions.controller;

import com.project.exhibitions.entity.User;
import com.project.exhibitions.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginFormController {

    private final UserService userService;

    public LoginFormController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public void loginUser(User user) throws UsernameNotFoundException {
        userService.loadUserByUsername(user.getUsername());
    }

}
