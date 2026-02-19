package com.example.test.demandlane.service.impl;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.repository.BookRepository;
import com.example.test.demandlane.service.ManageBookService;
import com.example.test.demandlane.util.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageBookServiceImp implements ManageBookService {

    private static final Logger log = LoggerFactory.getLogger(ManageBookServiceImp.class);
    private final BookRepository bookRepository;

    private final BookValidator bookValidator;

    @Override
    public List<Book> getAllBook() {
        List<Book> books = bookRepository.findAll();
        String traceId = MDC.get("traceId");
        log.info("Success Get Data Books [{}] : {}", traceId, books);
        return books;
    }

    @Override
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        String traceId = MDC.get("traceId");
        log.info("Success Get Data Book [{}] : {}", traceId, book);
        return book;
    }

    @Override
    public Book addBook(RequestBook book) {
        bookValidator.validateIsbnUnique(book.isbn());
        Book newBook = Book.builder()
                .title(book.title())
                .author(book.author())
                .isbn(book.isbn())
                .totalCopies(book.totalCopies())
                .availableCopies(book.totalCopies())
                .build();
        Book savedBook = bookRepository.save(newBook);
        String traceId = MDC.get("traceId");
        log.info("Success Add Data Book [{}] : {}", traceId, savedBook);
        return savedBook;
    }

    @Override
    public Book updateBook(Long id, RequestBook request) {
        Book updateBook = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Book not found"));

        bookValidator.validateUpdate(request.isbn(), updateBook);

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
            int borrowedCopies = updateBook.getTotalCopies() - updateBook.getAvailableCopies();
            updateBook.setTotalCopies(request.totalCopies());
            updateBook.setAvailableCopies(request.totalCopies() - borrowedCopies);
        }

        Book savedBook = bookRepository.save(updateBook);
        String traceId = MDC.get("traceId");
        log.info("Success Update Data Book [{}] : {}", traceId, savedBook);
        return savedBook;
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookValidator.validateIdUsed(id);
        bookRepository.delete(book);
        String traceId = MDC.get("traceId");
        log.info("Success Delete Data Book [{}] : {}", traceId, book);
    }
}
