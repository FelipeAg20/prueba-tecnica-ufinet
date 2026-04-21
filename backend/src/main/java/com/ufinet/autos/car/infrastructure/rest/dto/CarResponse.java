package com.ufinet.autos.car.infrastructure.rest.dto;

import com.ufinet.autos.car.domain.model.Car;

public record CarResponse(
        Long id,
        String brand,
        String model,
        Integer year,
        String plateNumber,
        String color
) {
    public static CarResponse fromDomain(Car car) {
        return new CarResponse(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPlateNumber(),
                car.getColor()
        );
    }
}
