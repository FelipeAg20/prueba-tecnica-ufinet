package com.ufinet.autos.car.infrastructure.persistence;

import com.ufinet.autos.auth.infrastructure.persistence.UserEntity;
import com.ufinet.autos.car.domain.model.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "plate_number", nullable = false, unique = true, length = 20)
    private String plateNumber;

    @Column(nullable = false, length = 50)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public static CarEntity fromDomain(Car car, UserEntity userEntity) {
        return CarEntity.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .plateNumber(car.getPlateNumber())
                .color(car.getColor())
                .user(userEntity)
                .build();
    }

    public Car toDomain() {
        return new Car(id, brand, model, year, plateNumber, color, user.getId());
    }
}
