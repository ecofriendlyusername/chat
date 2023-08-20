package com.example.demo.api;

import com.example.demo.application.ChatRoomService;
import com.example.demo.dto.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/alluserchatrooms")
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        List<ChatRoomResponseDto> chatRoomResponseDtos = chatRoomService.fetchAllChatRooms(email);
        return new ResponseEntity<>(chatRoomResponseDtos, HttpStatus.OK);
    }
}
