package com.example.demo.application;

import com.example.demo.dto.ChatRoomResponseDto;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.MainChannel;
import com.example.demo.entity.Member;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MainChannelRepository;
import com.example.demo.repository.MemberRepository;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // private final
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void makeAChatRoom(List<String> memberEmails, String destination) {
        ChatRoom chatRoom = ChatRoom.builder()
                .destination(destination)
                .build();

        List<Member> members = new ArrayList<>();

        for (String memberEmail : memberEmails) {
            Member member = memberRepository.findByEmail(memberEmail);
            if (member == null) continue;
            List<ChatRoom> chatRooms = member.getChatRooms();
            chatRooms.add(chatRoom);
//            member.addChatRoom(chatRoom);
            members.add(member);
            memberRepository.save(member);
        }

        chatRoom.setMembers(members);

        chatRoomRepository.save(chatRoom);
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
                .id(chatRoom.getId())
                .build();
        return itemMatchResponsePageDto;
    }
}
