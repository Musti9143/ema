package com.mdzdmr.ema.dto;

import jakarta.validation.constraints.Email;

public record UpdateEmployeeRequest(
        String firstName,
        String lastName,
        @Email(message = "Not a valid email!")
        String email,
        String jobTitle) { }
