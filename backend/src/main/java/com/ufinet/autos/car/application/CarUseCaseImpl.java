package com.ufinet.autos.car.application;

import com.ufinet.autos.car.domain.model.Car;
import com.ufinet.autos.car.domain.ports.input.CarUseCase;
import com.ufinet.autos.car.domain.ports.output.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarUseCaseImpl implements CarUseCase {

    private final CarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> findAll(Long userId) {
        return carRepository.findAllByUserId(userId);
    }

    @Override
    public List<Car> search(Long userId, String plate, String model, String brand, Integer year) {
        return carRepository.search(userId, plate, model, brand, year);
    }

    @Override
    public Car update(Long carId, Long userId, String brand, String model,
                      Integer year, String plateNumber, String color) {
        Car car = carRepository.findByIdAndUserId(carId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        car.update(brand, model, year, plateNumber, color);
        return carRepository.save(car);
    }

    @Override
    public void delete(Long carId, Long userId) {
        Car car = carRepository.findByIdAndUserId(carId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        carRepository.delete(car);
    }
}
