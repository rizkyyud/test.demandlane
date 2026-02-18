package com.example.test.demandlane.repository;

import com.example.test.demandlane.model.entity.Loan;
import com.example.test.demandlane.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByMemberAndReturnedAtIsNull(Member member);
    boolean existsByMemberAndReturnedAtIsNullAndDueDateBefore(Member member, LocalDate dueDate);
    boolean existsByBookId(Long bookId);
    boolean existsByMemberId(Long memberId);
}
