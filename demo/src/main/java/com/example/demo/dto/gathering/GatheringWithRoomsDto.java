package com.example.demo.dto.gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GatheringWithRoomsDto {
    Long gatheringId;

    String gatheringName;

    List<ChatRoomSimpleDto> chatRooms;

    List<VoiceRoomSimpleDto> voiceRooms;

    @Builder
    GatheringWithRoomsDto(Long gatheringId, String gatheringName, List<ChatRoomSimpleDto> chatRooms, List<VoiceRoomSimpleDto> voiceRooms) {
        this.gatheringId = gatheringId;
        this.gatheringName = gatheringName;
        this.chatRooms = chatRooms;
        this.voiceRooms = voiceRooms;
    }
}
