package com.example.demo.application;

import com.example.demo.dto.notification.StompMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StompMessageService {
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendStompMessage(String destination, StompMessage notification) {
        messagingTemplate.convertAndSend("/topic/"+destination, notification);
    }

    public void sendStompMessage(List<String> destinations, StompMessage notification) {
        for (String mainChannelDestination : destinations) {
            messagingTemplate.convertAndSend("/topic/"+mainChannelDestination, notification);
        }
    }
}
