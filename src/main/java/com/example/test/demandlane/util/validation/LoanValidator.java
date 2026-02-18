package com.example.test.demandlane.util.validation;

import com.example.test.demandlane.exception.BadRequestException;
import com.example.test.demandlane.model.dto.request.RequestLoan;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.model.entity.Loan;
import com.example.test.demandlane.model.entity.Member;
import com.example.test.demandlane.repository.BookRepository;
import com.example.test.demandlane.repository.LoanRepository;
import com.example.test.demandlane.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LoanValidator {

    @Value("${max.active.loan:3}")
    private int maxActiveLoan;

    @Value("${max.day.loan:14}")
    private int maxDayLoan;

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public  void validate(Member member, Book book){
        validateBookAvailable(book);
        validateMaxActiveLoans(member);
        validateMaxDayLoans(member);
    }

    private void validateMaxActiveLoans(Member member) {
        long activeLoans =
                loanRepository.countByMemberAndReturnedAtIsNull(member);

        if (activeLoans >= maxActiveLoan) {
            throw new BadRequestException("Maximum active loans");
        }
    }

    private void validateMaxDayLoans(Member member) {
        boolean isOverDue = loanRepository.existsByMemberAndReturnedAtIsNullAndDueDateBefore(
                member,
                LocalDate.now()
        );
        if (isOverDue) {
            throw new BadRequestException("Member has overdue loans book");
        }
    }

    public void validateBookAvailable(Book book) {
        if(book.getAvailableCopies() < 1 ){
            throw new RuntimeException("Book not available");
        }
    }

    public void validateHasReturnedAt(LocalDate returnedAt) {
        if(returnedAt != null){
            throw new BadRequestException("Loan has returned at " + returnedAt);
        }
    }
}
