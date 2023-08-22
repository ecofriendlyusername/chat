package com.example.demo.application;

import com.example.demo.dto.ChatMessageDto;
import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.exception.RequestNotAuthorizedException;
import com.example.demo.exception.WrongMemberEntryException;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    public void saveMessage(ChatMessageDto webSocketChatMessage, String email, String destination) {
        ChatRoom chatRoom = chatRoomRepository.findByDestination(destination);

        ChatMessage chatMessage = ChatMessage.builder()
//                .member(member)
                .sender(email)
                .chatRoom(chatRoom)
                .type(webSocketChatMessage.getType())
                .content(webSocketChatMessage.getContent())
                .build();

        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageDto> getLatestChatMessages(String email, String destination, int amount) throws WrongMemberEntryException, RequestNotAuthorizedException {
        ChatRoom chatRoom = chatRoomRepository.findByDestination(destination);
        if (chatRoom == null) {
            throw new NoSuchElementException("Chat Room Doesn't Exist");
        }
        List<Member> members = chatRoom.getMembers();
        boolean access = false;
        for (Member member : members) {
            String memberEmail = member.getEmail();
            if (memberEmail == null) throw new WrongMemberEntryException();
            if (member.getEmail().equals(email)) {
                access = true;
                break;
            }
        }
        if (!access) throw new RequestNotAuthorizedException();
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByOrderByTimeStampDesc(PageRequest.of(0, amount));

        return convertToChatMessageDtoList(chatMessages);
    }

    private List<ChatMessageDto> convertToChatMessageDtoList(List<ChatMessage> chatMessages) {
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .id(chatMessage.getId())
                    .content(chatMessage.getContent())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender()).build();
            chatMessageDtos.add(chatMessageDto);
        }
        return chatMessageDtos;
    }
}
