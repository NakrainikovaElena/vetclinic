package com.example.vetclinic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;
}
