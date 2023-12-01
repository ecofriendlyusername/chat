package com.example.demo.api;

import com.example.demo.application.GatheringService;
import com.example.demo.application.MemberService;
import com.example.demo.dto.gathering.*;
import com.example.demo.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gathering")
@RequiredArgsConstructor
public class GatheringController {
    private final GatheringService gatheringService;

    private final MemberService memberService;

    @GetMapping("/alluserchatrooms")
    @Operation(summary = "로그인 되어있는 유저가 속한 모든 모임을 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<GatheringResponseDto>> getMyGatherings(@AuthenticationPrincipal OAuth2User principal) {
        Member member = memberService.getMember(principal);
        List<GatheringResponseDto> gatherings = gatheringService.fetchAllMyGatherings(member);
        return new ResponseEntity<>(gatherings, HttpStatus.OK);
    }

    @PostMapping("/makechatroom/{gatheringId}")
    @Operation(summary = "모임에 채팅방을 만듬. ", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<ChatRoomSimpleDto> makeAChatRoomInGathering(@AuthenticationPrincipal OAuth2User principal, @PathVariable Long gatheringId, @RequestPart ChatRoomInGatheringCreationRequestDto chatRoom) {
        Member member = memberService.getMember(principal);
        ChatRoomSimpleDto chatRoomCreated = gatheringService.makeAChatRoom(chatRoom, gatheringId, member);
        return new ResponseEntity<>(chatRoomCreated, HttpStatus.OK);
    }

    @PostMapping("/makegathering")
    @Operation(summary = "모임 생성", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<GatheringResponseDto> makeAGathering(@AuthenticationPrincipal OAuth2User principal, @RequestPart("gathering_image") MultipartFile gatheringImage, @RequestPart GatheringCreationRequestDto gathering) throws IOException {
        Member member = memberService.getMember(principal);
        GatheringResponseDto createdGathering = gatheringService.makeAGathering(gatheringImage, gathering, member);
        return new ResponseEntity<>(createdGathering, HttpStatus.OK);
    }

    @PostMapping("/makegatheringwithemails")
    @Operation(summary = "모임 생성, 초기 멤버들 이메일과 함께", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<GatheringResponseDto> makeAGathering(@AuthenticationPrincipal OAuth2User principal, @RequestPart("gathering_image") MultipartFile gatheringImage, @RequestPart GatheringCreationRequestWithParticipantsDto gathering) throws IOException {
        Member member = memberService.getMember(principal);
        GatheringResponseDto createdGathering = gatheringService.makeAGatheringWithParticipants(gatheringImage, gathering, member);
        return new ResponseEntity<>(createdGathering, HttpStatus.OK);
    }
}
