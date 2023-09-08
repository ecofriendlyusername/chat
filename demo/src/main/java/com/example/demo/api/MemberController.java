package com.example.demo.api;

import com.example.demo.application.MemberService;
import com.example.demo.dto.member.MemberInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    @Operation(summary = "특정 유저의 정보(유저네임, 이메일)를 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@AuthenticationPrincipal OAuth2User principal, @PathVariable Long memberId) {
        MemberInfoResponseDto memberInfo = memberService.getMemberInfo(memberId);

        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }
}