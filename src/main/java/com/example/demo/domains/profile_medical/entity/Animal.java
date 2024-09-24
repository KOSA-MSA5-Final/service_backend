package com.example.demo.domains.profile_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Animal {
    @Id @GeneratedValue
    @Column(name = "animal_id")
    private Long id;
    @Column(name = "animal_name")
    private String name;

    @OneToOne
    @Column(name = "profile_id")
    private Profile profile;
}
