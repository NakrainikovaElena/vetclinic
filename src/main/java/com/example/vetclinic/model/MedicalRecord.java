package com.example.vetclinic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medical_records")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "pet_id", unique = true)
    private Pet pet;

    @Column(columnDefinition = "TEXT")
    private String recordText;
}
