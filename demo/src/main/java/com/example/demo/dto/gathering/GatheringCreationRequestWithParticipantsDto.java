package com.example.demo.dto.gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GatheringCreationRequestWithParticipantsDto {
    private String gatheringName;

    List<String> participants;

    @Builder
    GatheringCreationRequestWithParticipantsDto(String gatheringName, List<String> participants) {
        this.gatheringName = gatheringName;
        this.participants = participants;
    }
}
