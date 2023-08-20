package com.example.demo.dto;

import com.example.demo.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Setter
@NoArgsConstructor
@Getter
public class ChatRoomResponseDto {
    private Long id;

    private List<String> memberEmails;

    private String destination;

    @Builder
    ChatRoomResponseDto(Long id, String destination, List<String> memberEmails) {
        this.id = id;
        this.destination = destination;
        this.memberEmails = memberEmails;
    }
}
