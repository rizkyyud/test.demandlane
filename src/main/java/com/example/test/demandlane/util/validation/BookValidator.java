package com.example.test.demandlane.util.validation;

import com.example.test.demandlane.exception.BadRequestException;
import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.repository.BookRepository;
import com.example.test.demandlane.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public void validateIsbnUnique(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new BadRequestException("ISBN is already in use");
        }
    }

    public void validateUpdate(String newIsbn, Book existingBook) {

        if (bookRepository.existsByIsbn(newIsbn)
                && !existingBook.getIsbn().equals(newIsbn)) {
            throw new BadRequestException("ISBN is already in use");
        }

        int borrowed =
                existingBook.getTotalCopies()
                        - existingBook.getAvailableCopies();

        if (borrowed > existingBook.getTotalCopies()) {
            throw new BadRequestException(
                    "Invalid book stock"
            );
        }
    }

    public void validateIdUsed(Long id) {
        if (bookRepository.existsById(id)) {
            throw new BadRequestException("Book ID is already in use");
        }
    }

}
