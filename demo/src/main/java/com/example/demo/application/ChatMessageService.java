package com.example.demo.application;

import com.example.demo.dto.chat.ChatMessageDto;
import com.example.demo.dto.chat.ChatMessageResponseDto;
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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final MemberInChatRoomRepository memberInChatRoomRepository;
    public void saveMessage(ChatMessageDto webSocketChatMessage, String email) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(webSocketChatMessage.getChatRoomId());
        if (optionalChatRoom.isEmpty()) {
            System.out.println("chat room doesn't exist");
            return;
        }
        ChatRoom chatRoom = optionalChatRoom.get();
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(email)
                .chatRoom(chatRoom)
                .type(webSocketChatMessage.getType())
                .content(webSocketChatMessage.getContent())
                .build();

        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageResponseDto> getLatestChatMessages(Member member, String destination, int amount) throws RequestNotAuthorizedException {
        ChatRoom chatRoom = chatRoomRepository.findByDestination(destination);

        if (!memberInChatRoomRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new RequestNotAuthorizedException();
        }

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomOrderByTimeStampDesc(chatRoom, PageRequest.of(0, amount));

        List<ChatMessageResponseDto> chatMessageResponseDtos = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageResponseDto chatMessageResponseDto = ChatMessageResponseDto.builder()
                    .id(chatMessage.getId())
                    .content(chatMessage.getContent())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender()).build();
            chatMessageResponseDtos.add(chatMessageResponseDto);
        }
        return chatMessageResponseDtos;
    }
}
