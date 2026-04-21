package com.ufinet.autos.car.infrastructure.persistence;

import com.ufinet.autos.auth.infrastructure.persistence.UserJpaRepository;
import com.ufinet.autos.car.domain.model.Car;
import com.ufinet.autos.car.domain.ports.output.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarPersistenceAdapter implements CarRepository {

    private final CarJpaRepository carJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Car save(Car car) {
        var userEntity = userJpaRepository.findById(car.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return carJpaRepository.save(CarEntity.fromDomain(car, userEntity)).toDomain();
    }

    @Override
    public List<Car> findAllByUserId(Long userId) {
        return carJpaRepository.findAllByUserId(userId)
                .stream().map(CarEntity::toDomain).toList();
    }

    @Override
    public Optional<Car> findByIdAndUserId(Long id, Long userId) {
        return carJpaRepository.findByIdAndUserId(id, userId).map(CarEntity::toDomain);
    }

    @Override
    public List<Car> search(Long userId, String plate, String model, String brand, Integer year) {
        Specification<CarEntity> spec = CarSpecification.byUserId(userId);

        if (!isBlank(plate))  spec = spec.and(CarSpecification.plateContains(plate));
        if (!isBlank(model))  spec = spec.and(CarSpecification.modelContains(model));
        if (!isBlank(brand))  spec = spec.and(CarSpecification.brandContains(brand));
        if (year != null)     spec = spec.and(CarSpecification.yearEquals(year));

        return carJpaRepository.findAll(spec).stream().map(CarEntity::toDomain).toList();
    }

    @Override
    public void delete(Car car) {
        carJpaRepository.findByIdAndUserId(car.getId(), car.getUserId())
                .ifPresent(carJpaRepository::delete);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
