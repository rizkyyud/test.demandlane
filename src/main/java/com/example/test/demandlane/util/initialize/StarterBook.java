package com.example.test.demandlane.util.initialize;

import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StarterBook implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {

            bookRepository.save(new Book(null,
                    "Book Tes 1",
                    "Tester 1",
                    "1111111",
                    2,
                    2));

            bookRepository.save(new Book(null,
                    "Book Tes 2",
                    "Tester 2",
                    "2222222",
                    3,
                    3));

            bookRepository.save(new Book(null,
                    "Book Tes 3",
                    "Tester 3",
                    "33333333",
                    4,
                    4));
        }
    }
}
