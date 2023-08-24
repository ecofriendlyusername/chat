package com.example.demo.application;

import com.example.demo.dto.chat.ChatMessageDto;
import com.example.demo.entity.ChatMessage;
import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.exception.RequestNotAuthorizedException;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberInChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final MemberInChatRoomRepository memberInChatRoomRepository;
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

    public List<ChatMessageDto> getLatestChatMessages(Member member, String destination, int amount) throws RequestNotAuthorizedException {
        ChatRoom chatRoom = chatRoomRepository.findByDestination(destination);

        if (!memberInChatRoomRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new RequestNotAuthorizedException();
        }

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomOrderByTimeStampDesc(chatRoom, PageRequest.of(0, amount));

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
