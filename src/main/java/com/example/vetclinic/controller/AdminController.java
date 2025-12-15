package com.example.vetclinic.controller;

import com.example.vetclinic.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admin/addDoctor")
    public String addDoctor(@RequestParam String login,
                            @RequestParam String password,
                            @RequestParam String fullName,
                            @RequestParam String phone,
                            @RequestParam String specialization) {
        adminService.addDoctor(login, password, fullName, phone, specialization);

        return "redirect:/admin";
    }
}
