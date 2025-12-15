package com.example.vetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationPageController {

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "registerPage";
    }
}
