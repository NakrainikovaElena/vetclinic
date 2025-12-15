package com.example.vetclinic.controller;

import com.example.vetclinic.dto.*;
import com.example.vetclinic.model.Pet;
import com.example.vetclinic.model.PetOwner;
import com.example.vetclinic.model.Specialist;
import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.PetOwnerRepository;
import com.example.vetclinic.repository.PetRepository;
import com.example.vetclinic.repository.SpecialistRepository;
import com.example.vetclinic.repository.UserRepository;
import com.example.vetclinic.service.OwnerService;
import com.example.vetclinic.service.PetOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final PetOwnerService petOwnerService;
    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final PetRepository petRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final OwnerService ownerService;

    @GetMapping("/pets")
    public List<PetInfoDTO> getMyPets(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + userDetails.getUsername()));
        return petOwnerService.getPetsByOwner(user);
    }

    @PostMapping("/pets")
    public PetInfoDTO addPet(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddPetDTO dto) {
        User user = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();

        Specialist specialist = specialistRepository.findById(dto.getSpecialistId())
                .orElseThrow(() -> new IllegalArgumentException("Специалист не найден"));

        Pet pet = new Pet();
        pet.setPetName(dto.getPetName());
        pet.setSpecies(dto.getSpecies());
        pet.setSpecialist(specialist);
        petRepository.save(pet);

        PetOwner petOwner = new PetOwner();
        petOwner.setPet(pet);
        petOwner.setOwner(user);
        petOwnerRepository.save(petOwner);

        return new PetInfoDTO(pet.getPetId(),
                pet.getPetName(),
                pet.getSpecies(),
                specialist.getUser().getFullName(),
                specialist.getSpecialization(),
                specialist.getUser().getPhone());
    }

    @GetMapping("/all-pets")
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @GetMapping("/specialists")
    public List<SpecialistDTO> getAllSpecialists() {
        return specialistRepository.findAll().stream()
                .map(s -> new SpecialistDTO(
                        s.getSpecialistId(),
                        s.getUser().getFullName(),
                        s.getSpecialization()
                ))
                .toList();
    }

    @GetMapping("/my-pets")
    public List<PetSelectDTO> getMyPetsForSelect(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + userDetails.getUsername()));

        return petOwnerRepository.findByOwner(user).stream()
                .map(po -> new PetSelectDTO(po.getPet().getPetId(), po.getPet().getPetName(), po.getPet().getSpecies()))
                .toList();
    }

    @PostMapping("/owners")
    public ResponseEntity<String> addOwner(@RequestBody AddOwnerDTO dto) {
        try {
            String result = ownerService.addOwnerToPet(dto.getPetId(), dto.getOwnerLogin());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Внутренняя ошибка сервера");
        }
    }
}