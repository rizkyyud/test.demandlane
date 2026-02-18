package com.example.test.demandlane.util.validation;

import com.example.test.demandlane.exception.BadRequestException;
import com.example.test.demandlane.model.dto.request.RequestMember;
import com.example.test.demandlane.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validate(RequestMember member) {
        if (memberRepository.existsByEmail(member.email())){
            throw new BadRequestException("Email already exists");
        }
    }
}
