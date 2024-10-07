package com.example.demo.domains.profile_medical.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProfileDTO {
    private Long id;                    // Profile ID
    private String name;                // Profile name
    private String gender;              // Profile gender
    private Integer age;                // Profile age
    private Date birthday;              // Profile birthday
    private String isNeutered;          // Is neutered
    private String willNeutered;        // Will neutered
    private String pictureUrl;          // Profile picture URL
    private String isCurrent;           // Is current profile
    private String animalType;          // Animal type (from AnimalDetail entity)
}
