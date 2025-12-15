package com.example.vetclinic.repository;

import com.example.vetclinic.model.Pet;
import com.example.vetclinic.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findBySpecialist(Specialist specialist);
}
