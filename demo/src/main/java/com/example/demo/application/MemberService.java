package com.example.demo.application;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(OAuth2User principal) {
        String email = principal.getAttribute("email");
        Member member = memberRepository.findByEmail(email);
        return member;
    }
}
