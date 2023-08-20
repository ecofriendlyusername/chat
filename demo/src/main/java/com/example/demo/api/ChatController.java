package com.example.demo.api;

import com.example.demo.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ChatController {
    // private final RabbitTemplate rabbitTemplate;

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/{destination}")
    public void sendMessage(@Payload ChatMessage webSocketChatMessage, Authentication authentication, @DestinationVariable String destination) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();

        System.out.println("logged in user email : " + defaultOidcUser.getEmail());
        System.out.println("destination : " + destination);

        System.out.println(webSocketChatMessage.getContent() + " !");

        messagingTemplate.convertAndSend("/topic/"+destination, webSocketChatMessage);
    }

    @MessageMapping("/chat.sendMessage2")
    @SendTo("/topic/x")
    public ChatMessage sendMessage2(@Payload ChatMessage webSocketChatMessage, Authentication authentication) {
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();

        System.out.println(webSocketChatMessage.getContent() + " !");
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/x")
    public ChatMessage newUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}