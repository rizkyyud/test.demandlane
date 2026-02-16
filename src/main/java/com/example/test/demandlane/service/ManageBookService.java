package com.example.test.demandlane.service;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ManageBookService {

    public List<Book> getAllBook();
    public Book getBookById(Long id);
    public Book addBook(RequestBook book) throws BadRequestException;
    public Book updateBook(Long id, RequestBook book) throws BadRequestException;
    public void deleteBook(Long id);
}
