package com.example.test.demandlane.service;

import com.example.test.demandlane.model.dto.request.RequestLoan;
import com.example.test.demandlane.model.entity.Loan;

public interface ManageLoanService {
    public Loan createLoan(RequestLoan loan);
    public Loan returnLoan(Long loanId);
}
