package com.example.vetclinic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue
    private Long petId;

    private String petName;
    private String species;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

    @OneToMany(mappedBy = "pet")
    private List<PetOwner> petOwners;

}
