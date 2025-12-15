package com.example.vetclinic.service;

import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        int roleId = user.getRoleId().getRoleId();
        String roleName;

        switch (roleId) {
            case 1 -> roleName = "CLIENT";
            case 2 -> roleName = "DOCTOR";
            case 3 -> roleName = "ADMIN";
            default -> roleName = "UNKNOWN";
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roleName)
                .build();
    }
}

