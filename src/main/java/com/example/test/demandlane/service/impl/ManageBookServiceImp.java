package com.example.test.demandlane.service.impl;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.repository.BookRepository;
import com.example.test.demandlane.service.ManageBookService;
import com.example.test.demandlane.util.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageBookServiceImp implements ManageBookService {

    private final BookRepository bookRepository;

    private final BookValidator bookValidator;

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
    }

    @Override
    public Book addBook(RequestBook book) throws BadRequestException {
        bookValidator.validateAddBook(book);
        Book newBook = Book.builder()
                .title(book.title())
                .author(book.author())
                .isbn(book.isbn())
                .totalCopies(book.totalCopies())
                .availableCopies(book.totalCopies())
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Book updateBook(Long id, RequestBook request) throws BadRequestException {
        Book updateBook = getBookById(id);
        bookValidator.validateUpdateBook(request, updateBook);

        if (request.title() != null && !request.title().isBlank()) {
            updateBook.setTitle(request.title());
        }

        if (request.author() != null && !request.author().isBlank()) {
            updateBook.setAuthor(request.author());
        }

        if (request.isbn() != null && !request.isbn().isBlank()) {
            updateBook.setIsbn(request.isbn());
        }

        if (request.totalCopies() != null) {
            updateBook.setTotalCopies(request.totalCopies());
        }

        return bookRepository.save(updateBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}
