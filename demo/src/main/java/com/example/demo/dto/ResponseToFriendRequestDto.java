package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseToFriendRequestDto {
    private Long friendRequestId;
    private boolean accept;
}
