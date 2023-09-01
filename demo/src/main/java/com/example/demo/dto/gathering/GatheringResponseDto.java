package com.example.demo.dto.gathering;

import com.example.demo.entity.Gathering;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GatheringResponseDto {
    String gatheringName;
    Long gatheringId;
    String owner;

    public static GatheringResponseDto convertGatheringToGatheringResponseDto(Gathering gathering, String memberEmail) {
        GatheringResponseDto gatheringResponseDto = GatheringResponseDto.builder()
                .gatheringName(gathering.getGatheringName())
                .gatheringId(gathering.getId())
                .owner(memberEmail)
                .build();

        return gatheringResponseDto;
    }

    @Builder
    GatheringResponseDto(String gatheringName, String owner, Long gatheringId) {
        this.gatheringId = gatheringId;
        this.owner = owner;
        this.gatheringName = gatheringName;
    }
}
