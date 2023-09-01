package com.example.demo.application;

import com.example.demo.dto.member.MemberInfoResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(OAuth2User principal) {
        String email = principal.getAttribute("email");
        Member member = memberRepository.findByEmail(email);
        return member;
    }

    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isEmpty()) return null;

        return MemberInfoResponseDto.converMemberToMemberInfoResponseDto(member.get());
    }


}
