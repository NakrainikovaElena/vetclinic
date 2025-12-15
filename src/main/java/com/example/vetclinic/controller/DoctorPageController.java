package com.example.vetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorPageController {

    @GetMapping("/page")
    public String doctorPage() {
        return "doctorPage";
    }
}