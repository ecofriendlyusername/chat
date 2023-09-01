package com.example.demo.dto.gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatheringCreationRequestDto {
    private String gatheringName;

    @Builder
    GatheringCreationRequestDto(String gatheringName) {
        this.gatheringName = gatheringName;
    }
}
