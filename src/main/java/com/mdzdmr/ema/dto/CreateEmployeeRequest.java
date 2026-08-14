package com.mdzdmr.ema.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeRequest(
        @NotBlank(message = "Firstname cannot be empty!")
        String firstName,
        @NotBlank(message = "Lastname cannot be empty!")
        String lastName,
        @NotBlank(message = "Don't forget email!")
        @Email
        String email,
        String jobTitle) {
}
