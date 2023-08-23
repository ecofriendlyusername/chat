package com.example.demo.application;

import com.example.demo.dto.MainChannelDto;
import com.example.demo.dto.MainChannelMessage;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.MainChannel;
import com.example.demo.entity.Member;
import com.example.demo.repository.MainChannelRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // private final
public class MainChannelService {

    private final MemberRepository memberRepository;

    private final SimpMessageSendingOperations messagingTemplate;
    private final MainChannelRepository mainChannelRepository;
    public MainChannelDto fetchMainChannel(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            System.out.println("throw some custom exception here");
            return null;
        }

        MainChannel mainChannel = member.getMainChannel();

        MainChannelDto mainChannelDto = MainChannelDto
        .builder()
                .id(mainChannel.getId())
                .destination(mainChannel.getDestination())
                .memberEmail(email).build();

        return mainChannelDto;
    }

    public void makeAMainChannel(Member member) {
        MainChannel mainChannel = MainChannel.builder().destination(String.valueOf(member.getId())).member(member).build();
        mainChannelRepository.save(mainChannel);
        member.setMainChannel(mainChannel);
        memberRepository.save(member);
    }

    public void sendInvitations(List<String> invitees, ChatRoom chatRoom) {
        List<String> mainChannelDestinations = getMainChannels(invitees);

        MainChannelMessage invitation = MainChannelMessage.builder()
                .type("INVITATION")
                .chatRoomId(chatRoom.getId())
                .destination(chatRoom.getDestination())
                .build();

        for (String mainChannelDestination : mainChannelDestinations) {
            System.out.println(mainChannelDestination + " !!!!!!!");
            messagingTemplate.convertAndSend("/topic/"+mainChannelDestination, invitation);
        }
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
            MainChannel mainChannel = mainChannelRepository.findByMember(member);
            if (mainChannel == null) {
                // throw some exception here
                System.out.println("main channel for a member " + member.getEmail() + "doesn't exist");
                continue;
            }
            mainChannelDestinations.add(mainChannel.getDestination());
        }

        return mainChannelDestinations;
    }
}
