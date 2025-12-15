package com.example.vetclinic.repository;

import com.example.vetclinic.model.Pet;
import com.example.vetclinic.model.PetOwner;
import com.example.vetclinic.model.PetOwnerId;
import com.example.vetclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetOwnerRepository extends JpaRepository<PetOwner, PetOwnerId> {
    List<PetOwner> findByOwner(User owner);

    boolean existsByPetAndOwner(Pet pet, User owner);

    @Query("SELECT po FROM PetOwner po JOIN FETCH po.owner WHERE po.pet = :pet")
    List<PetOwner> findAllByPet(@Param("pet") Pet pet);
}
