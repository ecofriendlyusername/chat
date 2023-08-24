package com.example.demo.api;


import com.example.demo.application.FriendshipService;
import com.example.demo.application.MemberService;
import com.example.demo.dto.FriendRequestDto;
import com.example.demo.dto.ResponseToFriendRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RequestNotAuthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final String SUCCESS = "SUCCESS";
    private final String FAIL = "FAIL";
    private final MemberService memberService;
    private final FriendshipService friendshipService;

    // /friends/friendrequest
    @PostMapping("/friendrequest")
    @Operation(summary = "친구요청", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<String> friendRequest(@AuthenticationPrincipal OAuth2User principal, @RequestBody FriendRequestDto friendRequest) {
        Member member = memberService.getMember(principal);
        try {
            friendshipService.processFriendRequest(member, friendRequest);
        } catch (BadRequestException badRequestException) {
            return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }


    // /friends/handleresponsetofriendrequest
    @PostMapping("/handleresponsetofriendrequest")
    @Operation(summary = "친구 요청에 대한 답변(승낙/거절) 처리", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<String> handleResponseToFriendRequest(@AuthenticationPrincipal OAuth2User principal, @RequestBody ResponseToFriendRequestDto responseToFriendRequestDto) {
        Member member = memberService.getMember(principal);
        try {
            friendshipService.processResponseToFriendRequest(member, responseToFriendRequestDto);
        } catch (BadRequestException badRequestException) {
            return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RequestNotAuthorizedException requestNotAuthorizedException) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}
