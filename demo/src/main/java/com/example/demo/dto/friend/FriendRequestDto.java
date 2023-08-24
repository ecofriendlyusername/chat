package com.example.demo.dto.friend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class FriendRequestDto {
    private String strangerEmail;
    private String helloMessage;
}
