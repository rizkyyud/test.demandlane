package com.example.test.demandlane.service;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;

import java.util.List;

public interface ManageBookService {

    public List<Book> getAllBook();
    public Book getBookById(Long id);
    public Book addBook(RequestBook book);
    public Book updateBook(Long id, RequestBook book);
    public void deleteBook(Long id);
}
