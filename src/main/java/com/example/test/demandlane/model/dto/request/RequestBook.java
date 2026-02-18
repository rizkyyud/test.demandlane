package com.example.test.demandlane.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RequestBook(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotBlank(message = "Isbn is required")
        String isbn,

        @Min(value = 1, message = "Total copies must be greater than 0")
        Integer totalCopies
){}
