package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/util")
public class UtilController {

    @Autowired
    UserService userService;

    @GetMapping("/setPassword/{password}")
    public String setPassword(@PathVariable("password") String password) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.setPassword(userDetails.getUsername(), password);
        return "haha";
    }
}
