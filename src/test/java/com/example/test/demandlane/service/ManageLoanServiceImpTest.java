package com.example.test.demandlane.service;

import com.example.test.demandlane.model.dto.request.RequestLoan;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.model.entity.Loan;
import com.example.test.demandlane.model.entity.Member;
import com.example.test.demandlane.repository.BookRepository;
import com.example.test.demandlane.repository.LoanRepository;
import com.example.test.demandlane.repository.MemberRepository;
import com.example.test.demandlane.service.impl.ManageLoanServiceImp;
import com.example.test.demandlane.util.validation.LoanValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManageLoanServiceImpTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LoanValidator loanValidator;

    @InjectMocks
    private ManageLoanServiceImp loanService;

    @Test
    void createLoan_success() {
        Member member = new Member();
        member.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setAvailableCopies(5);

        RequestLoan request = new RequestLoan(1L, 1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.createLoan(request);

        assertNotNull(result);
        assertEquals(member, result.getMember());
        assertEquals(book, result.getBook());
        assertEquals(4, book.getAvailableCopies());
        assertNotNull(result.getBorrowedAt());
        assertEquals(LocalDate.now().plusDays(14), result.getDueDate());
        assertNull(result.getReturnedAt());

        verify(bookRepository).save(book);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void createLoan_memberNotFound_shouldThrow() {
        RequestLoan request = new RequestLoan(1L, 1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loanService.createLoan(request));

        assertEquals("Member not found", exception.getMessage());
    }
}
