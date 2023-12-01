package com.example.demo.application;

import com.example.demo.dto.gathering.*;
import com.example.demo.entity.*;
import com.example.demo.enums.RoleInGathering;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final MemberInGatheringRepository memberInGatheringRepository;

    private final MemberRepository memberRepository;

    private final MemberInChatRoomRepository memberInChatRoomRepository;

    private final ChatRoomInGatheringRepository chatRoomInGatheringRepository;

    private final FileHandlingService fileHandlingService;

    private final MemberService memberService;

    private final FileRepository fileRepository;
    public GatheringResponseDto makeAGathering(MultipartFile gatheringImage, GatheringCreationRequestDto gatheringCreationRequestDto, Member owner) throws IOException {
        File file = File.builder()
                .fileName(fileHandlingService.save(gatheringImage)).build();

        fileRepository.save(file);

        Gathering gathering = Gathering.builder()
                .gatheringImage(file)
                .gatheringName(gatheringCreationRequestDto.getGatheringName())
                .build();

        gatheringRepository.save(gathering);

        addMemberToGathering(gathering, owner, RoleInGathering.OWNER);

        return GatheringResponseDto.convertGatheringToGatheringResponseDto(gathering, owner.getEmail());
    }

    public GatheringResponseDto makeAGatheringWithParticipants(MultipartFile gatheringImage, GatheringCreationRequestWithParticipantsDto gatheringCreationRequestWithParticipantsDto, Member owner) throws IOException {
        File file = File.builder()
                .fileName(fileHandlingService.save(gatheringImage)).build();

        fileRepository.save(file);

        Gathering gathering = Gathering.builder()
                .gatheringImage(file)
                .gatheringName(gatheringCreationRequestWithParticipantsDto.getGatheringName())
                .build();

        gatheringRepository.save(gathering);

        for (String memberEmail : gatheringCreationRequestWithParticipantsDto.getParticipants()) {
            Member member = memberRepository.findByEmail(memberEmail);
            if (member == null) continue;
            addMemberToGathering(gathering, member, RoleInGathering.USER);
        }

        addMemberToGathering(gathering, owner, RoleInGathering.OWNER);

        return GatheringResponseDto.convertGatheringToGatheringResponseDto(gathering, owner.getEmail());
    }

    public void addMemberToGathering(Gathering gathering, Member member, RoleInGathering roleInGathering) {
        MemberInGathering memberInGathering = MemberInGathering.builder()
                .member(member)
                .gathering(gathering)
                .roleInGathering(roleInGathering).build();
        memberInGatheringRepository.save(memberInGathering);
    }

    public GatheringResponseDto makeAGatheringWithParticipants(MultipartFile gatheringImage, GatheringCreationRequestDto gatheringCreationRequestDto, Member member) throws IOException {
        File file = File.builder()
                .fileName(fileHandlingService.save(gatheringImage)).build();

        fileRepository.save(file);

        Gathering gathering = Gathering.builder()
                .gatheringImage(file)
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

    public ChatRoomSimpleDto makeAChatRoom(ChatRoomInGatheringCreationRequestDto chatRoomInGatheringCreationRequestDto, Long gatheringId, Member creator) {
        Optional<Gathering> optionalGathering = gatheringRepository.findById(gatheringId);

        if (optionalGathering.isEmpty()) {
            System.out.println("do something");
            return null;
        }

        Gathering gathering = optionalGathering.get();

        List<MemberInGathering> membersInGathering = memberInGatheringRepository.findAllByGathering(gathering);

        String destination = "chat-" + UUID.randomUUID();

        ChatRoom chatRoom = ChatRoom.builder()
                .destination(destination)
                .build();

        chatRoomRepository.save(chatRoom);

        ChatRoomInGathering chatRoomInGathering = ChatRoomInGathering.builder()
                .chatRoom(chatRoom)
                .gathering(gathering)
                .build();

        chatRoomInGatheringRepository.save(chatRoomInGathering);

        for (MemberInGathering memberInGathering : membersInGathering) {
            Member member = memberInGathering.getMember();
            MemberInChatRoom memberInChatRoom = MemberInChatRoom.builder().chatRoom(chatRoom).member(member).build();
            memberInChatRoomRepository.save(memberInChatRoom);
        }

        return ChatRoomSimpleDto.convertChatRoomToChatRoomSimpleDto(chatRoom);
    }
}
