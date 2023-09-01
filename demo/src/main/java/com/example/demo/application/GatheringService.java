package com.example.demo.application;

import com.example.demo.dto.gathering.GatheringCreationRequestDto;
import com.example.demo.dto.gathering.GatheringResponseDto;
import com.example.demo.entity.Gathering;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberInGathering;
import com.example.demo.enums.RoleInGathering;
import com.example.demo.repository.GatheringRepository;
import com.example.demo.repository.MemberInGatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;

    private final MemberInGatheringRepository memberInGatheringRepository;
    public Long makeAGathering(GatheringCreationRequestDto gatheringCreationRequestDto, Member member) {
        Gathering gathering = Gathering.builder()
                .gatheringName(gatheringCreationRequestDto.getGatheringName())
                .build();

        gatheringRepository.save(gathering);

        MemberInGathering memberInGathering = MemberInGathering.builder()
                .member(member)
                .gathering(gathering)
                .roleInGathering(RoleInGathering.OWNER).build();

        memberInGatheringRepository.save(memberInGathering);

        return gathering.getId();
    }

    public List<GatheringResponseDto> fetchAllMyGatherings(Member member) {
        List<MemberInGathering> memberInGatherings = memberInGatheringRepository.findAllByMember(member);

        List<GatheringResponseDto> gatheringResponseDtos = new ArrayList<>();

        for (MemberInGathering memberInGathering : memberInGatherings) {
            gatheringResponseDtos.add(GatheringResponseDto.convertGatheringToGatheringResponseDto(memberInGathering.getGathering()));
        }

        return gatheringResponseDtos;
    }
}
