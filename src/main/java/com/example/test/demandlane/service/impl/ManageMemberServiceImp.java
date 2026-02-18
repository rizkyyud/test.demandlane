package com.example.test.demandlane.service.impl;

import com.example.test.demandlane.model.dto.request.RequestMember;
import com.example.test.demandlane.model.entity.Member;
import com.example.test.demandlane.repository.MemberRepository;
import com.example.test.demandlane.service.ManageMemberService;
import com.example.test.demandlane.util.validation.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManageMemberServiceImp implements ManageMemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @Override
    public List<Member> getAllMember() {
        List<Member> members = memberRepository.findAll();
        String traceId = MDC.get("traceId");
        log.info("Success Get Data Members [{}] : {}", traceId, members);
        return members;
    }

    @Override
    public Member getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Member not found"));
        String traceId = MDC.get("traceId");
        log.info("Success Get Data Member [{}] : {}", traceId, member);
        return member;
    }

    @Override
    public Member addMember(RequestMember member) {
        memberValidator.validateEmailUnique(member.email());
        Member newMember = Member.builder()
                .name(member.name())
                .email(member.email())
                .build();
        Member savedMember = memberRepository.save(newMember);
        String traceId = MDC.get("traceId");
        log.info("Success Add Member [{}] : {}", traceId, member);
        return savedMember;
    }

    @Override
    public Member updateMember(Long id, RequestMember member) {
        memberValidator.validateEmailUnique(member.email());
        Member existMember = memberRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Member not found"));

        if (member.name() != null && !member.name().isBlank()) {
            existMember.setName(member.name());
        }
        if (member.email() != null && !member.email().isBlank()) {
            existMember.setEmail(member.email());
        }
        Member updatedMember = memberRepository.save(existMember);
        String traceId = MDC.get("traceId");
        log.info("Success Update Member [{}] : {}", traceId, updatedMember);
        return updatedMember;
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Member not found"));
        memberValidator.validateIdUsed(id);
        memberRepository.delete(member);
        String traceId = MDC.get("traceId");
        log.info("Success Delete Member [{}] : {}", traceId, member);
    }
}
