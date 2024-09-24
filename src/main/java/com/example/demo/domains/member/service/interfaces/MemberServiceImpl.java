package com.example.demo.domains.member.service.interfaces;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author : 나선주
 * date : 2024-09-24
 * description : MemberService
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24       나선주          최초 생성
 * 2024-09-24       나선주          메소드(조회, 삭제, 생성) 생성
 */
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

    @Override
    public Member findMemberByEmail(String email) {
        Member byEmail = memberRepository.findByEmail(email);
        return byEmail;
    }

    @Override
    public Boolean deleteMember(Member member) {
        try {
            memberRepository.delete(member);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
