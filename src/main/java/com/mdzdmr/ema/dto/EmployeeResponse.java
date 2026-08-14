package com.mdzdmr.ema.dto;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String jobTitle) {
}
