package com.example.demo.application;

import com.example.demo.dto.notification.RoomInvitationNotification;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.enums.NotificationType;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // private final
public class MainChannelService {

    private final MemberRepository memberRepository;

    private final StompMessageService stompMessageService;
    public void sendInvitations(List<String> invitees, ChatRoom chatRoom) {
        List<String> mainChannelDestinations = getMainChannels(invitees);

        RoomInvitationNotification invitation = RoomInvitationNotification.builder()
                .type(NotificationType.INVITATION)
                .chatRoomId(chatRoom.getId())
                .destination(chatRoom.getDestination())
                .build();
        stompMessageService.sendStompMessage(mainChannelDestinations, invitation);
    }

    public List<String> getMainChannels(List<String> memberEmails) {
        List<String> mainChannelDestinations = new ArrayList<>();

        for (String memberEmail : memberEmails) {
            Member member = memberRepository.findByEmail(memberEmail);
            if (member == null) {
                // throw some exception here
                System.out.println("member doesn't exist");
                continue;
            }

            mainChannelDestinations.add(member.getMainChannelDestination());
        }

        return mainChannelDestinations;
    }

    public String fetchMainChannelDestination(String email) {
        Member member = memberRepository.findByEmail(email);

        return member.getMainChannelDestination();
    }
}
