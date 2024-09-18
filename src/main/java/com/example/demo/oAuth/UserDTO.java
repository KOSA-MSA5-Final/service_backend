package com.example.demo.oAuth;

import com.example.demo.domains.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserDTO implements Serializable {
    private String name;
    private String email;
    private String picture;
    private String provider;
    private String providerId;

    public UserDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();

    }
}
