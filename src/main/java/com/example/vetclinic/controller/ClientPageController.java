package com.example.vetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientPageController {

    @GetMapping("/pets")
    public String clientPage() {
        return "clientPage";
    }
}
