package com.ufinet.autos.car.infrastructure.persistence;

import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    public static Specification<CarEntity> byUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<CarEntity> plateContains(String plate) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("plateNumber")), "%" + plate.toLowerCase() + "%");
    }

    public static Specification<CarEntity> modelContains(String model) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%");
    }

    public static Specification<CarEntity> brandContains(String brand) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<CarEntity> yearEquals(Integer year) {
        return (root, query, cb) -> cb.equal(root.get("year"), year);
    }
}
