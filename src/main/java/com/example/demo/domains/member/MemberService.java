package com.example.demo.domains.member;

import java.util.List;

public interface MemberService {
    Member saveMember(Member member);
    List<Member> findAllMembers();
    Member findByEmail(String email);
}
