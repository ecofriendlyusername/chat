package com.example.demo.api;

import com.example.demo.application.ChatRoomService;
import com.example.demo.application.MainChannelService;
import com.example.demo.dto.ChatRoomRequestDto;
import com.example.demo.dto.ChatRoomResponseDto;
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
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private final ChatRoomService chatRoomService;
    private final MainChannelService mainChannelService;

    @GetMapping("/alluserchatrooms")
    @Operation(summary = "로그인 되어있는 유저가 속한 모든 채팅방을 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        List<ChatRoomResponseDto> chatRoomResponseDtos = chatRoomService.fetchAllChatRooms(email);
        return new ResponseEntity<>(chatRoomResponseDtos, HttpStatus.OK);
    }

    @PostMapping("/makechatroom")
    @Operation(summary = "채팅방을 만듬. invitees에 초대하려는 유저들 이메일 넣어주세요", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<String> makeAChatRoom(@AuthenticationPrincipal OAuth2User principal, @RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        String email = principal.getAttribute("email");
        String destination = chatRoomService.makeAChatRoom(chatRoomRequestDto, email);
        mainChannelService.sendInvitations(chatRoomRequestDto, destination);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}
