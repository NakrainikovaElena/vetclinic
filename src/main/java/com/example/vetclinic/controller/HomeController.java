package com.example.vetclinic.controller;

import com.example.vetclinic.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        org.springframework.security.core.userdetails.User springUser =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Set<String> roles = springUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMIN")) return "redirect:/admin";
        if (roles.contains("ROLE_DOCTOR")) return "redirect:/doctor/page";
        if (roles.contains("ROLE_CLIENT")) return "redirect:/client/pets";

        return "mainPage";
    }

    @GetMapping("/main")
    public String home() {
        return "mainPage";
    }

    @GetMapping("/client")
    public String clientRedirect() { return "redirect:/client/pets";}

    @GetMapping("/doctor")
    public String doctorPage() {
        return "redirect:/doctor/patients";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }
}
