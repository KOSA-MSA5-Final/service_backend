package com.example.demo.domains.member;

public enum MemberRole {
    SIGNUP("SIGNUP"),  // 개인 사용자 역할
    LOGIN("LOGIN");      // 기업 사용자 역할

    private final String key;

    MemberRole(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
