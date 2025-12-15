package com.example.vetclinic.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class PetOwnerId implements Serializable {
    private Long pet;
    private int owner;
}
