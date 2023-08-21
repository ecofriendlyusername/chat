package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class MainChannelDto {
    private Long id;

    // private String member;
    private String memberEmail;

    private String destination;

    @Builder
    MainChannelDto(Long id, String destination, String memberEmail) {
        this.id = id;
        this.destination = destination;
        this.memberEmail = memberEmail;
    }
}
