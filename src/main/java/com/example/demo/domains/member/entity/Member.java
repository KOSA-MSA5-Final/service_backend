package com.example.demo.domains.member.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * author : 나선주
 * date : 2024-09-24
 * description : 회원
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
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private long member_id;

    @Column(nullable = false, name="member_name")
    private String name;
    @Column(nullable = false, name="member_email")
    private String email;
    @Column(nullable = false, name="member_password")
    private String password;

    @Column(name="member_telNum")
    private String telNum;


}
