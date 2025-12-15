package com.example.vetclinic.repository;

import com.example.vetclinic.model.Specialist;
import com.example.vetclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Optional<Specialist> findByUser(User user);
}
