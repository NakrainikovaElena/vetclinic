package com.example.vetclinic.repository;

import com.example.vetclinic.model.MedicalRecord;
import com.example.vetclinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByPet(Pet pet);
}
