package com.example.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetInfoDTO {
    private Long petId;
    private String petName;
    private String species;
    private String specialistName;
    private String specialistSpecialization;
    private String specialistPhone;
}
