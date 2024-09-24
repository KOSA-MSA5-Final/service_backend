package com.example.demo.domains.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long member_id;

    @Column(nullable = false)
    private String member_name;
    @Column(nullable = false)
    private String member_email;
    @Column(nullable = false)
    private String member_password;
    @Column
    private String member_contact_address;


}
