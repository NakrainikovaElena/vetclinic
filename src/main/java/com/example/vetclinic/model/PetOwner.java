package com.example.vetclinic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(PetOwnerId.class)
@Table(name = "pet_owners")
public class PetOwner {
    @Id
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Id
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String relation;
}
