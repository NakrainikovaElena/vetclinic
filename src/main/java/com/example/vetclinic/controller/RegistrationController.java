package com.example.vetclinic.controller;

import com.example.vetclinic.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String fullName,
            @RequestParam String phone
    ) {
        int clientRoleId = 1;

        String result = registrationService.registerUser(fullName, phone, login, password, clientRoleId);
        if (result.startsWith("Ошибка")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
