package com.bist.backendmodule.modules.car.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling the retrieval of all cars.
 */
@Service
public class GetAllCarsQueryHandler implements Query<Void, List<Car>> {
    private final CarRepository carRepository;

    public GetAllCarsQueryHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Executes the query to retrieve all cars.
     *
     * @param input The input parameter (unused in this query)
     * @return The ResponseEntity containing the list of all cars
     */
    @Override
    public ResponseEntity<List<Car>> execute(Void input) {
        List<Car> carList = carRepository.findAll();
        return ResponseEntity.ok().body(carList);
    }
}
