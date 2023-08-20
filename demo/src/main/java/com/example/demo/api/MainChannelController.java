package com.example.demo.api;

import com.example.demo.application.ChatRoomService;
import com.example.demo.application.MainChannelService;
import com.example.demo.dto.ChatRoomResponseDto;
import com.example.demo.entity.MainChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mainchannel")
@RequiredArgsConstructor
public class MainChannelController {
    private final ChatRoomService chatRoomService;
    private final MainChannelService mainchannelService;

    @GetMapping("/getmainchannel")
    public ResponseEntity<MainChannel> getMainChannel(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        MainChannel mainChannel = mainchannelService.fetchMainChannel(email);
        return new ResponseEntity<>(mainChannel, HttpStatus.OK);
    }
}
