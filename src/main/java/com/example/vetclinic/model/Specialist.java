package com.example.vetclinic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "specialists")
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specialistId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private String specialization;
}
