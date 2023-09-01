package com.example.demo.repository;

import com.example.demo.entity.ChatRoomInGathering;
import com.example.demo.entity.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomInGatheringRepository extends JpaRepository<ChatRoomInGathering, Long> {
    List<ChatRoomInGathering> findByGathering(Gathering gathering);
}
