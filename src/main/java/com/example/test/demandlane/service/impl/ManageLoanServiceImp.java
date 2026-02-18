package com.example.test.demandlane.service.impl;

import com.example.test.demandlane.model.dto.request.RequestLoan;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.model.entity.Loan;
import com.example.test.demandlane.model.entity.Member;
import com.example.test.demandlane.repository.BookRepository;
import com.example.test.demandlane.repository.LoanRepository;
import com.example.test.demandlane.repository.MemberRepository;
import com.example.test.demandlane.service.ManageLoanService;
import com.example.test.demandlane.util.validation.LoanValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageLoanServiceImp implements ManageLoanService {

    @Value("${max.day.loan:14}")
    private int maxDayLoan;

    private final LoanRepository loanRepository;
    private final LoanValidator loanValidator;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Loan createLoan(RequestLoan loan) {
        Member member = memberRepository.findById(loan.memberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Book book = bookRepository.findById(loan.bookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        loanValidator.validate(member, book);

        book.setTotalCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan newLoan = new Loan();
        newLoan.setMember(member);
        newLoan.setBook(book);
        newLoan.setBorrowedAt(LocalDate.now());
        newLoan.setDueDate(
                LocalDate.now()
                        .plusDays(maxDayLoan)
        );
        return loanRepository.save(newLoan);
    }

    @Override
    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan =  loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loanValidator.validateHasReturnedAt(loan.getReturnedAt());
        loan.setReturnedAt(LocalDate.now());
        loanRepository.save(loan);

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        return loan;
    }
}
