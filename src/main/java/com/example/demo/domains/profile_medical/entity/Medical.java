package com.example.demo.domains.profile_medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 진료 기록 Entity
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@Entity
@Getter
@Setter
public class Medical {
    @Id @GeneratedValue
    @Column(name="medical_id")
    private long id;

    @Column(name = "visit_date")
    private Date visit_date;

    @Column(name="receipt_img_url")
    private String receipt_img;

    @ManyToOne
    @Column(name = "profile_id")
    private Profile profile;
}
