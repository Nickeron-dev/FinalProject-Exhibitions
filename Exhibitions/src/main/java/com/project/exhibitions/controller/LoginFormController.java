package com.project.exhibitions.controller;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor

@Slf4j
@RestController
@RequestMapping("/")
@ResponseBody
public class LoginFormController {

    private final UserService userService;

    /**
     * This method logs user in
     * @param user DTO object of a user
     * @return Login page with result
     * @throws UsernameNotFoundException In case if user was not found in the database
     */
    @PostMapping("/login")
    @ResponseBody
    public String loginUser(UserDTO user) throws UsernameNotFoundException {
        userService.loadUserByUsername(user.getUsername());
        log.info("User logged in");
        return "login";
    }

}
