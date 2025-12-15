package com.example.vetclinic.dto;

import lombok.Data;

@Data
public class AddOwnerDTO {
    private Long petId;
    private String ownerLogin;
}
