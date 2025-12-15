package com.example.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorPetDTO {
    private Long petId;
    private String petName;
    private String species;
    private String owners;
    private String record;
}