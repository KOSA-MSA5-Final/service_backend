package com.example.demo.dtos;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.profile_medical.entity.Profile;
import lombok.Getter;

import java.util.List;

@Getter
public class UserAllInfoDTO {
    public Member member;
    public List<Profile> profiles;

    public UserAllInfoDTO(Member member, List<Profile> profiles) {
        this.member = member;
        this.profiles = profiles;
    }
}
