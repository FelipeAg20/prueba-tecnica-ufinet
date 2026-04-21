package com.ufinet.autos.car.domain.ports.input;

import com.ufinet.autos.car.domain.model.Car;

import java.util.List;

public interface CarUseCase {
    Car create(Car car);
    List<Car> findAll(Long userId);
    List<Car> search(Long userId, String plate, String model, String brand, Integer year);
    Car update(Long carId, Long userId, String brand, String model, Integer year, String plateNumber, String color);
    void delete(Long carId, Long userId);
}
