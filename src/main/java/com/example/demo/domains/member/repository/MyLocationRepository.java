package com.example.demo.domains.member.repository;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.MyLocation;
import com.example.demo.domains.member.repository.querydsl.customs.MyLocationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyLocationRepository extends JpaRepository<MyLocation, Long>, MyLocationRepositoryCustom {
    List<MyLocation> findByMember(Member Member);
}
