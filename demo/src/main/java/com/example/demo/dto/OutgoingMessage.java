package com.example.demo.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class OutgoingMessage {
    private String type;
    private String message;

    @Builder
    OutgoingMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
