package com.example.demo.application;

import com.example.demo.dto.ChatRoomRequestDto;
import com.example.demo.dto.ChatRoomResponseDto;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor // private final
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    public String makeAChatRoom(ChatRoomRequestDto chatRoomRequestDto, String roomMakerEmail) {
        // random string
        String destination = generateRandomString(20);

        ChatRoom chatRoom = ChatRoom.builder()
                .destination(destination)
                .roomName(chatRoomRequestDto.getRoomName())
                .build();

        List<String> invitees = chatRoomRequestDto.getInvitees();
        invitees.add(roomMakerEmail);

        List<Member> members = new ArrayList<>();

        for (String memberEmail : invitees) {
            Member member = memberRepository.findByEmail(memberEmail);
            if (member == null) continue;
            List<ChatRoom> chatRooms = member.getChatRooms();
            chatRooms.add(chatRoom);
            members.add(member);
            chatRoomRepository.save(chatRoom); // before : TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.example.demo.entity.ChatRoom
            memberRepository.save(member);
        }

        chatRoom.setMembers(members);

        chatRoomRepository.save(chatRoom);

        return destination;
    }

    private static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public List<ChatRoomResponseDto> fetchAllChatRooms(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            System.out.println("throw some custom exception here");
            return null;
        }

        List<ChatRoom> chatRooms = member.getChatRooms();

        List<ChatRoomResponseDto> chatRoomResponseDtos = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            chatRoomResponseDtos.add(convertToChatRoomResponseDto(chatRoom));
        }
        return chatRoomResponseDtos;
    }

    public ChatRoomResponseDto convertToChatRoomResponseDto(ChatRoom chatRoom) {
        List<Member> members = chatRoom.getMembers();
        List<String> memberEmails = new ArrayList<>();
        for (Member member : members) {
            memberEmails.add(member.getEmail());
        }
        ChatRoomResponseDto itemMatchResponsePageDto = ChatRoomResponseDto.builder()
                .destination(chatRoom.getDestination())
                .memberEmails(memberEmails)
                .roomName(chatRoom.getRoomName())
                .id(chatRoom.getId())
                .build();
        return itemMatchResponsePageDto;
    }
}
