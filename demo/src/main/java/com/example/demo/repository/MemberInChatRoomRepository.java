package com.example.demo.repository;

import com.example.demo.entity.ChatRoom;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberInChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberInChatRoomRepository extends JpaRepository<MemberInChatRoom, Long> {

    boolean existsByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    List<MemberInChatRoom> findByChatRoom(ChatRoom chatRoom);

    List<MemberInChatRoom> findByMember(Member member);
}
