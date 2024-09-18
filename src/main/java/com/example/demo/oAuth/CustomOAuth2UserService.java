package com.example.demo.oAuth;

import com.example.demo.domains.member.Member;
import com.example.demo.domains.member.MemberRepository;
import com.example.demo.domains.member.MemberRole;
import com.example.demo.domains.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService started....................................");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();


        OAuth2User oAuth2User = null;
        try {
            oAuth2User = delegate.loadUser(userRequest);
            log.info("hello ! : ", oAuth2User);
//            log.info(oAuth2User);
        } catch (OAuth2AuthenticationException e) {
            log.error("Error loading user: {}", e.getMessage(), e);
            throw e;
        }

        // 현재 로그인 진행 중인 서비스를 구분하는 코드 (네이버 로그인인지 구글 로그인인지 구분)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값 (Primary Key와 같은 의미)을 의미
        // 구글의 기본 코드는 "sub", 후에 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        System.out.println("userNameAttributeNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: " + userNameAttributeName);


        // OAuth2UserService를 통해 가져온 OAuthUser의 attribute를 담을 클래스 ( 네이버 등 다른 소셜 로그인도 이 클래스 사용)
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        System.out.println("attributesssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss: " + attributes);

        log.info("여기ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ");
        System.out.println("유저 정보다ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ::: "+oAuth2User.getAttributes());
        Member userEntity = saveOrUpdate(attributes);

        if (userEntity == null) {
            log.error("User not found in the database");
            throw new OAuth2AuthenticationException("User not found in the database");
        }

        httpSession.setAttribute("user", new UserDTO(userEntity));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(MemberRole.LOGIN.getKey()));

        System.out.println(attributes.getAttributes());
        Map<String, Object> userAttributes = attributes.getAttributes();
        if (userAttributes == null || userAttributes.isEmpty()) {
            log.error("User attributes are empty");
            throw new OAuth2AuthenticationException("User attributes are empty");

        }
        log.info("Heloooooooooooooooooooooooooooooooooooooooooooooo");

        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(
                authorities,
                userAttributes,
                attributes.getNameAttributeKey());
        log.info("defualtttttttttttttttttttttttttttttttttttttttttt");
        return defaultOAuth2User;
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        log.info("Saving user startttttttttttttt");
        String OAuthEmail = attributes.getEmail();

        List<Member> allMembers = memberService.findAllMembers();
        log.info("Saving user endttttttttttttt>>>>>>>>>>>>>>" + OAuthEmail);
//        String foundEmail = memberRepository.findByEmail(attributes.getEmail()).getEmail();

//        System.out.println("내래 이걸 찾았습니다: " + foundEmail);
        boolean isMemberExist = false;

        // 존재하는 회원 찾기
        for(Member member : allMembers) {
            if(member.getEmail().equals(OAuthEmail)) {
                isMemberExist = true;
                log.info("회원이 존재해요ㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛ" + member.getEmail());
                return member;
            }
        }

        // 회원정보 없을 시, 자동 회원가입

        if(!isMemberExist){
            System.out.println("회원따위 존재핮 ㅣ않아");
            Member member = new Member();
            member.setEmail(OAuthEmail);
            member.setName(attributes.getName());
            System.out.println("getNAME: " + attributes.getName());

            Member generatedMember = memberService.saveMember(member);
            if(generatedMember != null) {
                System.out.println("계정생성되었음");
            }
            return member;
        }

        return null;
    }

}