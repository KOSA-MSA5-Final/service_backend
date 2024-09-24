package com.example.demo.domains.profile_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Medical {
    @Id @GeneratedValue
    @Column(name="medical_id")
    private long id;

    @Column(name="receipt_img_url")
    private String receipt_img;

    @ManyToOne
    @Column(name = "profile_id")
    private Profile profile;


}
