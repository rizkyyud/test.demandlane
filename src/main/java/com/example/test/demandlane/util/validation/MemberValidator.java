package com.example.test.demandlane.util.validation;

import com.example.test.demandlane.exception.BadRequestException;
import com.example.test.demandlane.model.dto.request.RequestMember;
import com.example.test.demandlane.repository.LoanRepository;
import com.example.test.demandlane.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public void validateEmailUnique(String email) {
        if (memberRepository.existsByEmail(email)){
            throw new BadRequestException("Email already exists");
        }
    }

    public void validateIdUsed(Long id) {
        if(loanRepository.existsByMemberId(id)){
            throw new BadRequestException("Member Id already in use");
        }
    }
}
