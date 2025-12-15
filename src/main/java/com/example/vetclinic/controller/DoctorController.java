package com.example.vetclinic.controller;

import com.example.vetclinic.dto.DoctorPetDTO;
import com.example.vetclinic.model.MedicalRecord;
import com.example.vetclinic.model.Pet;
import com.example.vetclinic.model.Specialist;
import com.example.vetclinic.model.User;
import com.example.vetclinic.model.PetOwner;
import com.example.vetclinic.repository.MedicalRecordRepository;
import com.example.vetclinic.repository.PetOwnerRepository;
import com.example.vetclinic.repository.PetRepository;
import com.example.vetclinic.repository.SpecialistRepository;
import com.example.vetclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final PetRepository petRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;

    @GetMapping("/patients")
    public List<DoctorPetDTO> getMyPatients(Authentication authentication) {

        String login = authentication.getName();

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Specialist specialist = specialistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Доктор не найден"));

        List<Pet> pets = petRepository.findBySpecialist(specialist);

        return pets.stream().map(pet -> {

            List<PetOwner> ownersList = petOwnerRepository.findAllByPet(pet);

            String owners = pet.getPetOwners().stream()
                    .map(po -> po.getOwner().getFullName() + " (" + po.getOwner().getPhone() + ")")
                    .collect(Collectors.joining(", "));

            String record = medicalRecordRepository.findByPet(pet)
                    .map(MedicalRecord::getRecordText)
                    .orElse("Нет записи");

            return new DoctorPetDTO(
                    pet.getPetId(),
                    pet.getPetName(),
                    pet.getSpecies(),
                    owners,
                    record
            );
        }).toList();
    }

    @PostMapping("/update-diagnosis/{petId}")
    public String updateDiagnosis(@PathVariable Long petId, @RequestBody MedicalRecord recordDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Питомец не найден"));

        MedicalRecord record = medicalRecordRepository.findByPet(pet)
                .orElse(new MedicalRecord());

        record.setPet(pet);
        record.setRecordText(recordDto.getRecordText());
        medicalRecordRepository.save(record);

        return "success";
    }
}
