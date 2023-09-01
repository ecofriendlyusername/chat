package com.example.demo.application;

import com.example.demo.dto.gathering.ChatRoomInGatheringCreationRequestDto;
import com.example.demo.dto.gathering.ChatRoomSimpleDto;
import com.example.demo.dto.gathering.GatheringCreationRequestDto;
import com.example.demo.dto.gathering.GatheringResponseDto;
import com.example.demo.entity.*;
import com.example.demo.enums.RoleInGathering;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final MemberRepository memberRepository;

    private final MemberInChatRoomRepository memberInChatRoomRepository;

    private final ChatRoomInGatheringRepository chatRoomInGatheringRepository;

    private final MemberInGatheringRepository memberInGatheringRepository;
    public GatheringResponseDto makeAGathering(GatheringCreationRequestDto gatheringCreationRequestDto, Member member) {
        Gathering gathering = Gathering.builder()
                .gatheringName(gatheringCreationRequestDto.getGatheringName())
                .build();

        gatheringRepository.save(gathering);

        MemberInGathering memberInGathering = MemberInGathering.builder()
                .member(member)
                .gathering(gathering)
                .roleInGathering(RoleInGathering.OWNER).build();

        memberInGatheringRepository.save(memberInGathering);

        return GatheringResponseDto.convertGatheringToGatheringResponseDto(gathering, member.getEmail());
    }

    public List<GatheringResponseDto> fetchAllMyGatherings(Member member) {
        List<MemberInGathering> memberInGatherings = memberInGatheringRepository.findAllByMember(member);

        String memberEmail = member.getEmail();

        List<GatheringResponseDto> gatheringResponseDtos = new ArrayList<>();

        for (MemberInGathering memberInGathering : memberInGatherings) {
            gatheringResponseDtos.add(GatheringResponseDto.convertGatheringToGatheringResponseDto(memberInGathering.getGathering(), memberEmail));
        }

        return gatheringResponseDtos;
    }

    public ChatRoomSimpleDto makeAChatRoom(ChatRoomInGatheringCreationRequestDto chatRoomInGatheringCreationRequestDto, Long gatheringId, Member member) {
        String destination = "chat-" + UUID.randomUUID();

        ChatRoom chatRoom = ChatRoom.builder()
                .destination(destination)
                .build();

        chatRoomRepository.save(chatRoom);

        Optional<Gathering> optionalGathering = gatheringRepository.findById(gatheringId);

        if (optionalGathering.isEmpty()) {
            System.out.println("do something");
            return null;
        }

        ChatRoomInGathering chatRoomInGathering = ChatRoomInGathering.builder()
                .chatRoom(chatRoom)
                .gathering(optionalGathering.get())
                .build();

        chatRoomInGatheringRepository.save(chatRoomInGathering);

        return ChatRoomSimpleDto.convertChatRoomToChatRoomSimpleDto(chatRoom);
    }
}
