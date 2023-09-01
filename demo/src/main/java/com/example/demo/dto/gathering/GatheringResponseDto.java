package com.example.demo.dto.gathering;

import com.example.demo.entity.Gathering;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GatheringResponseDto {
    String gatheringName;
    String owner;

    public static GatheringResponseDto convertGatheringToGatheringResponseDto(Gathering gathering) {
        GatheringResponseDto gatheringResponseDto = GatheringResponseDto.builder()
                .gatheringName(gathering.getGatheringName())
                .owner(gathering.getMember().getEmail())
                .build();

        return gatheringResponseDto;
    }

    @Builder
    GatheringResponseDto(String gatheringName, String owner) {
        this.gatheringName = gatheringName;
    }
}
