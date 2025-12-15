package com.example.vetclinic.service;

import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByLogin(String login) {
        Optional<User> userOpt = userRepository.findByLogin(login);
        return userOpt.orElseThrow(() ->
                new RuntimeException("Пользователь с логином " + login + " не найден")
        );
    }

}
