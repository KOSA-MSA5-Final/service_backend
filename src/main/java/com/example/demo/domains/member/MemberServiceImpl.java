package com.example.demo.domains.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(Member member){
        Member m = memberRepository.save(member);
        return m;
    }

    public List<Member> findAllMembers(){
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email);
    }
}
