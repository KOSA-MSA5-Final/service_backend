package com.example.demo.domains.member.service.impls;

import com.example.demo.domains.member.dto.AddUserRequest;
import com.example.demo.domains.member.dto.JoinDTO;
import com.example.demo.domains.member.dto.MemberDTO;
import com.example.demo.domains.member.entity.Member;

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
 * 2024-09-24       나선주          method생성(CRD)
 */
public interface MemberService {
    //회원가입
    void joinProcess(JoinDTO joinDTO);
    //전체 회원 조회
    List<Member> findAllMembers();
    //특정 회원 조회
    Member findMemberByEmail(String email);
    //회원탈퇴
    Boolean deleteMember(Member member);
    //회원정보수정
    void updateMember(MemberDTO memberDTO);
}
