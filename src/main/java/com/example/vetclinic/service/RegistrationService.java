package com.example.vetclinic.service;

import com.example.vetclinic.model.Role;
import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.RoleRepository;
import com.example.vetclinic.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String registerUser(String fullName, String phone, String login, String password, int roleId) {

        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (roleOptional.isEmpty()) {
            return "Ошибка: указанной роли не существует";
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setLogin(login);
        user.setPassword(hashedPassword);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setRoleId(roleOptional.get());

        userRepository.save(user);

        return "Регистрация успешна";
    }
}
