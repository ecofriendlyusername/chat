package com.example.demo.api;


import com.example.demo.application.ChatRoomService;
import com.example.demo.application.GatheringService;
import com.example.demo.application.MainChannelService;
import com.example.demo.application.MemberService;
import com.example.demo.dto.gathering.GatheringCreationRequestDto;
import com.example.demo.dto.gathering.GatheringResponseDto;
import com.example.demo.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gathering")
@RequiredArgsConstructor
public class GatheringController {
    private final ChatRoomService chatRoomService;
    private final GatheringService gatheringService;
    private final MainChannelService mainChannelService;

    private final MemberService memberService;

    @GetMapping("/alluserchatrooms")
    @Operation(summary = "로그인 되어있는 유저가 속한 모든 채팅방을 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<GatheringResponseDto>> getMyGatherings(@AuthenticationPrincipal OAuth2User principal) {
        Member member = memberService.getMember(principal);
        List<GatheringResponseDto> gatherings = gatheringService.fetchAllMyGatherings(member);
        return new ResponseEntity<>(gatherings, HttpStatus.OK);
    }

    @PostMapping("/makegathering")
    @Operation(summary = "채팅방을 만듬. invitees에 초대하려는 유저들 이메일 넣어주세요", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<Long> makeAGathering(@AuthenticationPrincipal OAuth2User principal, @RequestBody GatheringCreationRequestDto gathering) {
        Member member =memberService.getMember(principal);
        Long gatheringId = gatheringService.makeAGathering(gathering, member);
        return new ResponseEntity<>(gatheringId, HttpStatus.OK);
    }
}
