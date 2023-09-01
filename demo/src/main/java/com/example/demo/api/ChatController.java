package com.example.demo.api;

import com.example.demo.application.ChatMessageService;
import com.example.demo.application.StompMessageService;
import com.example.demo.dto.chat.ChatMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final StompMessageService stompMessageService;

    private final ChatMessageService chatMessageService;


    @MessageMapping("/chat/{destination}")
    @Operation(summary = "채팅방의 모든 메시지들이 오는 엔드포인트", description = "type과 content를 지정 가능. ex) type = chat, content = (보내려는 메시지) "
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public void sendMessage(@Payload ChatMessageDto webSocketChatMessage, Authentication authentication, @DestinationVariable String destination) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();
        String memberEmail = defaultOidcUser.getEmail();


        webSocketChatMessage.setSender(memberEmail);

        chatMessageService.saveMessage(webSocketChatMessage, memberEmail);

        stompMessageService.sendStompMessage(destination, webSocketChatMessage);
    }
}