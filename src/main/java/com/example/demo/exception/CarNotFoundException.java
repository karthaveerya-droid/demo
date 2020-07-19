package com.example.demo.exception;

public class CarNotFoundException extends Exception {
        public CarNotFoundException(long carId) {
            super(String.format("Car is not found with id : '%s'", carId));
        }
    }
