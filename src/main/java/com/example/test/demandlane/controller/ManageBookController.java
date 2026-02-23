package com.example.test.demandlane.controller;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.dto.response.ApiResponse;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.service.ManageBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandlane/book")
@RequiredArgsConstructor
public class ManageBookController {

    private final ManageBookService manageBookService;
    private final String traceId = MDC.get("traceId");

    @PostMapping("/manage")
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody RequestBook request) {
        Book book = manageBookService.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(traceId, "Book added successfully", book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        Book book = manageBookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success(traceId, "Book found", book));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBook(){
        List<Book> books = manageBookService.getAllBook();
        return ResponseEntity.ok(ApiResponse.success(traceId, "Success get all books", books));
    }

    @PatchMapping("/manage/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id, @Valid @RequestBody RequestBook request) {
        Book book = manageBookService.updateBook(id, request);
        return ResponseEntity.ok(ApiResponse.success(traceId, "Success update book", book));
    }

    @DeleteMapping("/manage/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        manageBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
