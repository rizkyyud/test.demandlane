package com.example.test.demandlane.controller;

import com.example.test.demandlane.model.dto.request.RequestBook;
import com.example.test.demandlane.model.entity.Book;
import com.example.test.demandlane.service.ManageBookService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandlane/book")
@RequiredArgsConstructor
public class ManageBookController {

    private final ManageBookService manageBookService;

    @PostMapping("/addNew")
    public ResponseEntity<Book> addBook(@RequestBody RequestBook request) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageBookService.addBook(request));
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok().body(manageBookService.getBookById(id));
    }

    @GetMapping("/getBooks")
    public ResponseEntity<List<Book>> getAllBook(){
        return ResponseEntity.ok().body(manageBookService.getAllBook());
    }

    @PatchMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody RequestBook request) throws BadRequestException {
        return ResponseEntity.ok().body(manageBookService.updateBook(id, request));
    }

    @DeleteMapping("deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        manageBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
