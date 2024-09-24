package com.example.demo.domains.profile_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AnimalDetail {
    @Id @GeneratedValue
    @Column(name = "animalDetail_id")
    private long id;

    @Column(name = "animalDetail_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

}
