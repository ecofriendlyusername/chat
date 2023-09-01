package com.example.demo.dto.gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GatheringCreationRequestDto {
    private String gatheringName;

    @Builder
    GatheringCreationRequestDto(String gatheringName) {
        this.gatheringName = gatheringName;
    }
}
