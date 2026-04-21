package com.ufinet.autos.car.domain.ports.output;

import com.ufinet.autos.car.domain.model.Car;

import java.util.List;
import java.util.Optional;

// Puerto de salida: el dominio define qué necesita, sin saber cómo se implementa
public interface CarRepository {
    Car save(Car car);
    List<Car> findAllByUserId(Long userId);
    Optional<Car> findByIdAndUserId(Long id, Long userId);
    List<Car> search(Long userId, String plate, String model, String brand, Integer year);
    void delete(Car car);
}
