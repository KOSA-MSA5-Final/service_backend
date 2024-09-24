package com.example.demo.domains.profile_medical.entity;

import com.example.demo.domains.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "profile_name")
    private String name;

    @Column(name = "profile_age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "member_id") // 외래 키를 명시적으로 지정
    private Member member;

    @ManyToOne
    @JoinColumn(name = "animalDetail_id") // 외래 키를 명시적으로 지정
    private AnimalDetail animalDetail;
}
