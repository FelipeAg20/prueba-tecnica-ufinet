package com.ufinet.autos.car.infrastructure.rest.dto;

import jakarta.validation.constraints.*;

public record CarRequest(
        @NotBlank @Size(max = 100) String brand,
        @NotBlank @Size(max = 100) String model,
        @NotNull @Min(1900) @Max(2100) Integer year,
        @NotBlank @Size(max = 20) String plateNumber,
        @NotBlank @Size(max = 50) String color
) {}
