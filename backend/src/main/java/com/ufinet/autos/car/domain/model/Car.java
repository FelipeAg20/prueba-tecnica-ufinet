package com.ufinet.autos.car.domain.model;

public class Car {

    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String plateNumber;
    private String color;
    private Long userId;

    public Car(Long id, String brand, String model, Integer year,
               String plateNumber, String color, Long userId) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plateNumber = plateNumber;
        this.color = color;
        this.userId = userId;
    }

    public static Car create(String brand, String model, Integer year,
                             String plateNumber, String color, Long userId) {
        return new Car(null, brand, model, year, plateNumber, color, userId);
    }

    public void update(String brand, String model, Integer year, String plateNumber, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plateNumber = plateNumber;
        this.color = color;
    }

    public Long getId()           { return id; }
    public String getBrand()      { return brand; }
    public String getModel()      { return model; }
    public Integer getYear()      { return year; }
    public String getPlateNumber(){ return plateNumber; }
    public String getColor()      { return color; }
    public Long getUserId()       { return userId; }
}
