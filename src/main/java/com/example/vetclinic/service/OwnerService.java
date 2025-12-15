package com.example.vetclinic.service;

import com.example.vetclinic.model.Pet;
import com.example.vetclinic.model.PetOwner;
import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.PetOwnerRepository;
import com.example.vetclinic.repository.PetRepository;
import com.example.vetclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetOwnerRepository petOwnerRepository;

    @Transactional
    public String addOwnerToPet(Long petId, String ownerLogin) {
        Optional<User> ownerOpt = userRepository.findByLogin(ownerLogin);
        if (ownerOpt.isEmpty())
            return "Пользователя с таким логином не существует";

        Optional<Pet> petOpt = petRepository.findById(petId);
        if (petOpt.isEmpty())
            return "Питомец не найден";

        Pet pet = petOpt.get();
        User owner = ownerOpt.get();

        List<PetOwner> petOwners = pet.getPetOwners() != null ? pet.getPetOwners() : List.of();
        boolean alreadyOwner = petOwners.stream()
                .anyMatch(po -> po.getOwner().getUserId() == owner.getUserId());
        if (alreadyOwner)
            return "Этот пользователь уже является хозяином выбранного питомца";

        PetOwner petOwner = new PetOwner();
        petOwner.setPet(pet);
        petOwner.setOwner(owner);
        petOwnerRepository.save(petOwner);

        return "Хозяин успешно добавлен";
    }
}
