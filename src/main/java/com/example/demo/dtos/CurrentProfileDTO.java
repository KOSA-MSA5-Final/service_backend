package com.example.demo.dtos;

import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.entity.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CurrentProfileDTO {
    private Long id;

    private String name;

    private Integer age;

    private AnimalDetail animalDetail;

    private String pictureUrl;

    public CurrentProfileDTO(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.age = profile.getAge();
        this.pictureUrl = profile.getPictureUrl();
        this.animalDetail = profile.getAnimalDetail();
    }
}
