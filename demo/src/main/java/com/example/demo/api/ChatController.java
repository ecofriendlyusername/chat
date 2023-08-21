package com.example.demo.api;

import com.example.demo.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/{destination}")
    public void sendMessage(@Payload ChatMessage webSocketChatMessage, Authentication authentication, @DestinationVariable String destination) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();

        System.out.println("logged in user email : " + defaultOidcUser.getEmail());
        System.out.println("destination : " + destination);

        System.out.println(webSocketChatMessage.getContent() + " !");

        messagingTemplate.convertAndSend("/topic/"+destination, webSocketChatMessage);
    }
}