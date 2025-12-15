package com.example.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialistDTO {
    private Long specialistId;
    private String userFullName;
    private String specialization;
}
