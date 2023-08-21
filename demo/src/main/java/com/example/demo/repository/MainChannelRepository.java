package com.example.demo.repository;

import com.example.demo.entity.MainChannel;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainChannelRepository extends JpaRepository<MainChannel, Long> {
    MainChannel findByMember(Member member);
}
