package com.example.demo.domains.disease.entity;

import com.example.demo.domains.profile_medical.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * author : 나선주
 * date : 2024-09-24
 * description : DiseaseSubProfile(join)
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24       나선주          최초 생성
 */

@Entity
@Getter
@Setter
public class DiseaseSubProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="disease_sub_id")
    private DiseaseSub diseaseSub;

    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;
}
