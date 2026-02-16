package com.example.test.demandlane.model.entity;

import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "loan")
public class Loan {

    private Long id;
    private Long bookId;
    private Long memberId;
    private Date borrowedAt;
    private Date dueDate;
    private Date returnedAt;
}
