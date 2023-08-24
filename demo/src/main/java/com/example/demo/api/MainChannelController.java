package com.example.demo.api;

import com.example.demo.application.ChatRoomService;
import com.example.demo.application.MainChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mainchannel")
@RequiredArgsConstructor
public class MainChannelController {
    private final ChatRoomService chatRoomService;
    private final MainChannelService mainchannelService;

    @GetMapping("/getmainchannel")
    @Operation(summary = "유저의 메인채널을 가져옴", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<String> getMainChannel(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        String mainChannelDestination = mainchannelService.fetchMainChannelDestination(email);

        return new ResponseEntity<>(mainChannelDestination, HttpStatus.OK);
    }
}
