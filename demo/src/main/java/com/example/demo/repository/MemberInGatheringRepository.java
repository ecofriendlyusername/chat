package com.example.demo.repository;

import com.example.demo.entity.Gathering;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberInGathering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberInGatheringRepository extends JpaRepository<MemberInGathering, Long> {
    List<MemberInGathering> findAllByMember(Member member);

    List<MemberInGathering> findByMember(Member member);

    List<MemberInGathering> findAllByGathering(Gathering gathering);
}
