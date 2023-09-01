package com.example.demo.application;

import com.example.demo.dto.chat.ChatRoomInvitationRequestDto;
import com.example.demo.dto.chat.ChatRoomRequestDto;
import com.example.demo.dto.chat.ChatRoomResponseDto;
import com.example.demo.entity.*;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemberInChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor // private final
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final MemberInChatRoomRepository memberInChatRoomRepository;

    public ChatRoom makeAChatRoom(ChatRoomRequestDto chatRoomRequestDto, String roomMakerEmail) {
        String destination = "chat-" + UUID.randomUUID();

        ChatRoom chatRoom = ChatRoom.builder()
                .destination(destination)
                .build();

        List<String> invitees = chatRoomRequestDto.getInvitees();

        invitees.add(roomMakerEmail);

        chatRoomRepository.save(chatRoom);

        addMembersHelper(chatRoom, invitees);

        return chatRoom;
    }

    public List<ChatRoomResponseDto> fetchAllChatRooms(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            System.out.println("throw some custom exception here");
            return null;
        }

        List<MemberInChatRoom> memberInChatRooms = memberInChatRoomRepository.findByMember(member);

        List<ChatRoomResponseDto> chatRoomResponseDtos = new ArrayList<>();

        for (MemberInChatRoom memberInChatRoom : memberInChatRooms) {
            ChatRoom chatRoom = memberInChatRoom.getChatRoom();
            chatRoomResponseDtos.add(convertToChatRoomResponseDto(chatRoom));
        }
        return chatRoomResponseDtos;
    }

    public ChatRoomResponseDto convertToChatRoomResponseDto(ChatRoom chatRoom) {
        List<MemberInChatRoom> memberInDirectChatRooms = memberInChatRoomRepository.findByChatRoom(chatRoom);

        List<String> memberEmails = new ArrayList<>();
        for (MemberInChatRoom memberInChatRoom : memberInDirectChatRooms) {
            Member member = memberInChatRoom.getMember();
            memberEmails.add(member.getEmail());
        }
        ChatRoomResponseDto itemMatchResponsePageDto = ChatRoomResponseDto.builder()
                .destination(chatRoom.getDestination())
                .memberEmails(memberEmails)
                .id(chatRoom.getId())
                .build();

        return itemMatchResponsePageDto;
    }

    public ChatRoom isInChatRoom(ChatRoomInvitationRequestDto chatRoomInvitationRequestDto, String email) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomInvitationRequestDto.getChatRoomId());
        Member member = memberRepository.findByEmail(email);

        if (optionalChatRoom.isEmpty() || member == null) {
            // or exception????
            return null;
        }
        ChatRoom chatRoom = optionalChatRoom.get();
        if (memberInChatRoomRepository.existsByMemberAndChatRoom(member, chatRoom)) return chatRoom;
        return null;
    }

    public void addMembers(ChatRoom chatRoom, ChatRoomInvitationRequestDto chatRoomInvitationRequestDto) {
        addMembersHelper(chatRoom, chatRoomInvitationRequestDto.getInvitees());
    }

    private void addMembersHelper(ChatRoom chatRoom, List<String> invitees) {
        for (String memberEmail : invitees) {
            Member member = memberRepository.findByEmail(memberEmail);
            if (member == null) continue;

            MemberInChatRoom memberInDirectChatRoom = MemberInChatRoom.builder()
                    .chatRoom(chatRoom)
                    .member(member)
                    .build();

            memberInChatRoomRepository.save(memberInDirectChatRoom);
//            chatRoomRepository.save(chatRoom);
//            memberRepository.save(member);
        }
    }

    public ChatRoomResponseDto fetchAllChatOneRoom(String email) {
        return null;
    }


}
