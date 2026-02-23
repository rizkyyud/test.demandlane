package com.example.test.demandlane.controller;

import com.example.test.demandlane.model.dto.request.RequestLoan;
import com.example.test.demandlane.model.dto.response.ApiResponse;
import com.example.test.demandlane.model.entity.Loan;
import com.example.test.demandlane.service.ManageLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demandlane/loan")
@RequiredArgsConstructor
public class ManageLoanController {

    private final ManageLoanService manageLoanService;
    private final String traceId = MDC.get("tracId");

    @PostMapping
    public ResponseEntity<ApiResponse<Loan>> createLoan(@Valid @RequestBody RequestLoan loan) {

        Loan newLoan = manageLoanService.createLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(traceId,"Loan creat successfully", newLoan));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Loan>> returnLoan(@PathVariable Long id) {
        Loan loan = manageLoanService.returnLoan(id);
        return ResponseEntity.ok(ApiResponse.success(traceId, "Loan update successfully", loan));
    }
}
