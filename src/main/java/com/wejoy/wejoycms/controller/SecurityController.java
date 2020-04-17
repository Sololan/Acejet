package com.wejoy.wejoycms.controller;

import com.wejoy.wejoycms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @Value("${picturepath.nginx-url}")
    String nginxUrl;

    @RequestMapping("/{urlPath}")
    String frontControl(Model model, HttpSession httpSession, @PathVariable("urlPath") String urlPath) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("curUser", userDetails.getUsername());
        model.addAttribute("urlPath", urlPath);
        httpSession.setAttribute("nginxUrl", nginxUrl);
        return "pages/security/" + urlPath + ".html";
    }

    @RequestMapping("/{urlPath}/{id}")
    String anaFrontControl(Model model, HttpSession httpSession, @PathVariable("urlPath") String urlPath, @PathVariable("id") String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("curUser", userDetails.getUsername());
        model.addAttribute("urlPath", urlPath);
        httpSession.setAttribute("nginxUrl", nginxUrl);
        return "pages/security/" + urlPath + ".html";
    }

}
