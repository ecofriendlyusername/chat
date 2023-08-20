package com.example.demo.application;

import com.example.demo.entity.MainChannel;
import com.example.demo.entity.Member;
import com.example.demo.repository.MainChannelRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // private final
public class MainChannelService {

    private final MemberRepository memberRepository;
    private final MainChannelRepository mainChannelRepository;
    public MainChannel fetchMainChannel(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            System.out.println("throw some custom exception here");
            return null;
        }

        MainChannel mainChannel = member.getMainChannel();

        return mainChannel;
    }

    public void makeAMainChannel(Member member) {
        MainChannel mainChannel = MainChannel.builder().destination(String.valueOf(member.getId())).member(member).build();
        mainChannelRepository.save(mainChannel);
        memberRepository.save(member);
    }
}
