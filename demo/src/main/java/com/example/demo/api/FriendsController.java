package com.example.demo.api;


import com.example.demo.application.FriendshipService;
import com.example.demo.application.MemberService;
import com.example.demo.dto.friend.FriendDto;
import com.example.demo.dto.friend.FriendRequestDto;
import com.example.demo.dto.ResponseToFriendRequestDto;
import com.example.demo.dto.friend.PendingFriendRequestDto;
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

import java.util.List;

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

    @GetMapping("/friendrequests")
    @Operation(summary = "답을 기다리고 있는 친구요청들 가져오기", description = "웹소켓 커넥션이 열려있는 동안에는 바로 친구요청을 받을 수 있지만 로그아웃 상태였던 때에 받은 친구요청은 DB에 저장되므로 로그인 후 이 친구요청들을 가져오면 됩니다"
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<PendingFriendRequestDto>> getFriendRequests(@AuthenticationPrincipal OAuth2User principal) {
        List<PendingFriendRequestDto> friendRequests = friendshipService.fetchFriendRequests(memberService.getMember(principal));

        return new ResponseEntity<>(friendRequests, HttpStatus.OK);
    }

    // /friends/friends

    @GetMapping("/friends")
    @Operation(summary = "친구 리스트 가져오기", description = "..."
            , responses = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    public ResponseEntity<List<FriendDto>> getFriends(@AuthenticationPrincipal OAuth2User principal) {
        Member member = memberService.getMember(principal);
        List<FriendDto> friends = friendshipService.getFriends(member);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }


    // /friends/handleresponsetofriendrequest
    @PostMapping("/handleresponsetofriendrequest")
    @Operation(summary = "친구 요청에 대한 답변(승낙/거절) 처리", description = "승낙이면 accept에 true 거절이면 false"
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
