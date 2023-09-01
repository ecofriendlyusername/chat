package com.example.demo.dto.member;

import com.example.demo.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoResponseDto {
    Long id;
    String email;
    String pfpFileName;
    String name;

    public static MemberInfoResponseDto converMemberToMemberInfoResponseDto(Member member) {
        MemberInfoResponseDto memberInfoResponseDto = MemberInfoResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .pfpFileName(null)
                .build();
        return memberInfoResponseDto;
    }

    @Builder
    MemberInfoResponseDto(Long id, String email, String pfpFileName, String name) {
        this.id = id;
        this.email = email;
        this.pfpFileName = pfpFileName;
        this.name = name;
    }
}
