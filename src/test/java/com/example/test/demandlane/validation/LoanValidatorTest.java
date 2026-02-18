package com.example.test.demandlane.validation;

import com.example.test.demandlane.exception.BadRequestException;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.model.entity.Member;
import com.example.test.demandlane.repository.LoanRepository;
import com.example.test.demandlane.util.validation.LoanValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanValidatorTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanValidator loanValidator;

    @Test
    void validateBookAvailable_shouldThrow_whenNoStock() {
        Book book = new Book();
        book.setAvailableCopies(0);

        assertThrows(RuntimeException.class,
                () -> loanValidator.validateBookAvailable(book));
    }

    @Test
    void validateMaxActiveLoans_shouldThrow_whenExceeded() {
        Member member = new Member();

        when(loanRepository.countByMemberAndReturnedAtIsNull(member))
                .thenReturn(3L);

        assertThrows(BadRequestException.class,
                () -> loanValidator.validate(member, new Book()));
    }

    @Test
    void validateOverdue_shouldThrow_whenHasOverdue() {
        Member member = new Member();
        Book book = new Book();
        book.setAvailableCopies(5);

        when(loanRepository.countByMemberAndReturnedAtIsNull(member))
                .thenReturn(0L);

        when(loanRepository.existsByMemberAndReturnedAtIsNullAndDueDateBefore(
                member, LocalDate.now()))
                .thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> loanValidator.validate(member, book));
    }
}
