package com.example.demo.controller;

import com.example.demo.exception.CarNotFoundException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    CarRepository carRepository;
    private static final Logger logger=LoggerFactory.getLogger(CarController.class);


    @GetMapping("/cars")
    public List<Car> getAllCars() {
        logger.info("Retrieving Cars");
        return carRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/springhello")
    public String sayHello() {
        logger.info("Hello");
        return "Hello";
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody Car car) {
        logger.info("Adding Car");
        return carRepository.save(car);
    }
    @Cacheable(value = "cars", key = "#carId")
    @GetMapping("/car/{id}")
    public Car getCarById(@PathVariable(value = "id") Long carId) throws CarNotFoundException {
        logger.info("Retreiving Car with id " + carId);
        return carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
    }

    @CachePut(value = "cars", key = "#carId")
    @PutMapping("/car/{id}")
    public Car updateCar(@PathVariable(value = "id") Long carId,
                           @RequestBody Car carDetails) throws CarNotFoundException {
        logger.info("Updating Car with id " + carId);
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
        car.setMake_name(carDetails.getMake_name());
        //car.setModel_name(carDetails.getModel_name());
        //car.setVin(carDetails.getVin());
        return carRepository.save(car);
    }

    @CacheEvict(value = "cars", allEntries=true)
    @DeleteMapping("/car/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable(value = "id") Long carId) throws CarNotFoundException {
        logger.info("deleting Car with id " + carId);
        Car book = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));

        carRepository.delete(book);
        return ResponseEntity.ok().build();
    }
}
