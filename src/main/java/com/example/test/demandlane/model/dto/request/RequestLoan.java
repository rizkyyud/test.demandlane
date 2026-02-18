package com.example.test.demandlane.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record RequestLoan(
        @NotNull(message = "Book id required")
        Long bookId,

        @NotNull(message = "Member id required")
        Long memberId
) {
}
