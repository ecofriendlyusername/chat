package com.example.demo.api;

import com.example.demo.application.ChatRoomService;
import com.example.demo.application.MainChannelService;
import com.example.demo.dto.MainChannelDto;
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
    public ResponseEntity<MainChannelDto> getMainChannel(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        MainChannelDto mainChannelDto = mainchannelService.fetchMainChannel(email);
        if (mainChannelDto == null) {
            System.out.println("mainChannelDto null");
        } else {
            System.out.println(mainChannelDto.getMemberEmail() + "'s channel destination : " + mainChannelDto.getDestination());
        }
        return new ResponseEntity<>(mainChannelDto, HttpStatus.OK);
    }
}
