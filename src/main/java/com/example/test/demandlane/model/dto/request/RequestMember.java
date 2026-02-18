package com.example.test.demandlane.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RequestMember(
        @NotBlank(message = "Name is required")
        @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z0-9 ]+$",
        message = "Name must contain letters and cannot be only numbers or special characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) {}
