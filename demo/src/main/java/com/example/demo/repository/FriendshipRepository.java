package com.example.demo.repository;

import com.example.demo.entity.Friendship;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    boolean existsByMemberOneAndMemberTwo(Member member, Member stranger);

    List<Friendship> findAllByMemberOne(Member member);

    List<Friendship> findAllByMemberTwo(Member member);
}
