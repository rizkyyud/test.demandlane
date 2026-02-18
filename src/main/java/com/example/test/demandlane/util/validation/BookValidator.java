package com.example.test.demandlane.util.validation;

import com.example.test.demandlane.exception.BadRequestException;
import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    public void validateAddBook(RequestBook requestBook)  {
        if(bookRepository.existsByIsbn(requestBook.isbn())){
            throw new BadRequestException("Isbn is already in use");
        }
    }

    public void validateUpdateBook(RequestBook requestBook, Book existBook) throws BadRequestException {

        if(bookRepository.existsByIsbn(requestBook.isbn())
                && !existBook.getIsbn().equals(requestBook.isbn())){
            throw new BadRequestException("Isbn is already in use");
        }

        int borrowed = existBook.getTotalCopies() - existBook.getAvailableCopies();

        if (requestBook.totalCopies() < borrowed) {
            throw new BadRequestException("Total copies cannot be less than borrowed copies");
        }

    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
