package com.ufinet.autos.car.infrastructure.rest;

import com.ufinet.autos.auth.domain.ports.output.UserRepository;
import com.ufinet.autos.car.infrastructure.rest.dto.CarRequest;
import com.ufinet.autos.car.infrastructure.rest.dto.CarResponse;
import com.ufinet.autos.car.domain.model.Car;
import com.ufinet.autos.car.domain.ports.input.CarUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarUseCase carUseCase;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails.getUsername());
        return ResponseEntity.ok(carUseCase.findAll(userId).stream().map(CarResponse::fromDomain).toList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarResponse>> search(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String plate,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year) {

        Long userId = resolveUserId(userDetails.getUsername());
        return ResponseEntity.ok(
                carUseCase.search(userId, plate, model, brand, year)
                        .stream().map(CarResponse::fromDomain).toList()
        );
    }

    @PostMapping
    public ResponseEntity<CarResponse> create(@Valid @RequestBody CarRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails.getUsername());
        Car car = Car.create(request.brand(), request.model(), request.year(),
                request.plateNumber(), request.color(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CarResponse.fromDomain(carUseCase.create(car)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody CarRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails.getUsername());
        Car updated = carUseCase.update(id, userId, request.brand(), request.model(),
                request.year(), request.plateNumber(), request.color());
        return ResponseEntity.ok(CarResponse.fromDomain(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails.getUsername());
        carUseCase.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    private Long resolveUserId(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getId();
    }
}
