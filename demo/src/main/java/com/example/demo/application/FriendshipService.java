package com.example.demo.application;

import com.example.demo.dto.FriendRequestDto;
import com.example.demo.dto.ResponseToFriendRequestDto;
import com.example.demo.dto.notification.FriendRequestNotification;
import com.example.demo.entity.FriendRequest;
import com.example.demo.entity.Friendship;
import com.example.demo.entity.Member;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RequestNotAuthorizedException;
import com.example.demo.repository.FriendRequestRepository;
import com.example.demo.repository.FriendshipRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final MemberRepository memberRepository;

    private final StompMessageService stompMessageService;

    private final FriendRequestRepository friendRequestRepository;

    private final MainChannelService mainChannelService;

    private final FriendshipRepository friendshipRepository;
    public void processFriendRequest(Member from, FriendRequestDto friendRequestDto) throws BadRequestException {
        String strangerEmail = friendRequestDto.getStrangerEmail();
        String helloMessage = friendRequestDto.getHelloMessage();

        if (strangerEmail == null) {
            throw new BadRequestException("유저 이메일을 입력하지 않았습니다.");
        }

        Member to = memberRepository.findByEmail(strangerEmail);

        if (to == null) {
            throw new BadRequestException("친구 추가하려는 유저가 존재하지 않습니다.");
        }

        if (friendshipRepository.existsByMemberOneAndMemberTwo(from, to) ||
                friendshipRepository.existsByMemberOneAndMemberTwo(to, from)) {
            throw new BadRequestException("이미 친구입니다.");
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .helloMessage(helloMessage)
                .from(from)
                .to(to)
                .build();

        friendRequestRepository.save(friendRequest);

        String mainChannelDestination = to.getMainChannelDestination();

        FriendRequestNotification invitation = FriendRequestNotification.builder()
                .type("FRIEND_REQUEST")
                .friendRequestId(friendRequest.getId())
                .helloMessage(helloMessage)
                .from(from.getEmail())
                .build();

        stompMessageService.sendStompMessage(mainChannelDestination, invitation);
    }

    public void processResponseToFriendRequest(Member to, ResponseToFriendRequestDto responseToFriendRequestDto) throws BadRequestException, RequestNotAuthorizedException {
        Optional<FriendRequest> optionalFriendRequest = friendRequestRepository.findById(responseToFriendRequestDto.getFriendRequestId());

        if (optionalFriendRequest.isEmpty()) {
            throw new BadRequestException("해당 친구요청이 없습니다.");
        }

        FriendRequest friendRequest = optionalFriendRequest.get();

        if (friendRequest.getTo() != to) {
            throw new RequestNotAuthorizedException();
        }

        if (responseToFriendRequestDto.isAccept()) {
            makeFriends(friendRequest.getFrom(), friendRequest.getTo());
        }

        friendRequestRepository.delete(friendRequest);
    }
    public void makeFriends(Member from, Member to) {
        Friendship friendship = Friendship.builder()
                .memberOne(from)
                .memberTwo(to)
                .build();

        friendshipRepository.save(friendship);
    }
}
