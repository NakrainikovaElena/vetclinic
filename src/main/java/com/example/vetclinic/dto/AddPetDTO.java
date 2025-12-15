package com.example.vetclinic.dto;

import lombok.Data;

@Data
public class AddPetDTO {
    private String petName;
    private String species;
    private Long specialistId;
}
