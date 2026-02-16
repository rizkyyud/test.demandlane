package com.example.test.demandlane.util.validation;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    public void validateAddBook(RequestBook requestBook) throws BadRequestException {
        if(isNotBlank(requestBook.title())){
            throw new BadRequestException("Title is required");
        }
        if(isNotBlank(requestBook.author())){
            throw new BadRequestException("Author is required");
        }
        if(isNotBlank(requestBook.isbn())){
            if(bookRepository.existsByIsbn(requestBook.isbn())){
                throw new BadRequestException("Isbn is already in use");
            }
            throw new BadRequestException("Isbn is required");
        }
        if(requestBook.totalCopies() < 1){
            throw new BadRequestException("Total copies must be greater than 0");
        }
    }

    public void validateUpdateBook(RequestBook requestBook, Book existBook) throws BadRequestException {

        boolean validField = isNotBlank(requestBook.title()) || isNotBlank(requestBook.author())
                || isNotBlank(requestBook.isbn()) || requestBook.totalCopies() != null;

        if(!validField){
            throw new BadRequestException("At least one field must be provided");
        }

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
