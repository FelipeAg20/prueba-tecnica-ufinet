package com.ufinet.autos.car.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CarJpaRepository extends JpaRepository<CarEntity, Long>, JpaSpecificationExecutor<CarEntity> {
    List<CarEntity> findAllByUserId(Long userId);
    Optional<CarEntity> findByIdAndUserId(Long id, Long userId);
}
