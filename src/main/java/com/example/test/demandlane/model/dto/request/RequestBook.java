package com.example.test.demandlane.model.dto.request;

public record RequestBook(
        String title,
        String author,
        String isbn,
        Integer totalCopies
){}
