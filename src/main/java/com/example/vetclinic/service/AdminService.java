package com.example.vetclinic.service;

import com.example.vetclinic.model.Role;
import com.example.vetclinic.model.Specialist;
import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.RoleRepository;
import com.example.vetclinic.repository.SpecialistRepository;
import com.example.vetclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addDoctor(String login, String password, String fulName, String phone, String specialization) {
        if (userRepository.existsByLogin(login)) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        Role doctorRole = roleRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Роль DOCTOR не найдена"));

        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fulName);
        user.setPhone(phone);
        user.setRoleId(doctorRole);
        userRepository.save(user);

        Specialist specialist = new Specialist();
        specialist.setUser(user);
        specialist.setSpecialization(specialization.toLowerCase());
        specialistRepository.save(specialist);
    }
}
