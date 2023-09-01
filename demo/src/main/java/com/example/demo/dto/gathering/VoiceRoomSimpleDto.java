package com.example.demo.dto.gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class VoiceRoomSimpleDto {
    String identifier;
    Long id;

    @Builder
    VoiceRoomSimpleDto(String identifier, Long id) {
        this.identifier = identifier;
        this.id = id;
    }
}
