package com.example.vetclinic.service;

import com.example.vetclinic.dto.PetInfoDTO;
import com.example.vetclinic.model.PetOwner;
import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.PetOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetOwnerService {

    private final PetOwnerRepository petOwnerRepository;

    public PetOwnerService(PetOwnerRepository petOwnerRepository) {
        this.petOwnerRepository = petOwnerRepository;
    }

    public List<PetInfoDTO> getPetsByOwner(User owner) {
        List<PetOwner> petOwners = petOwnerRepository.findByOwner(owner);

        return petOwners.stream().map(po -> {
            var pet = po.getPet();
            var specialist = pet.getSpecialist();

            return new PetInfoDTO(
                    pet.getPetId(),
                    pet.getPetName(),
                    pet.getSpecies(),
                    specialist != null ? specialist.getUser().getFullName() : "",
                    specialist != null ? specialist.getSpecialization() : "",
                    specialist != null ? specialist.getUser().getPhone() : ""
            );
        }).collect(Collectors.toList());
    }
}
