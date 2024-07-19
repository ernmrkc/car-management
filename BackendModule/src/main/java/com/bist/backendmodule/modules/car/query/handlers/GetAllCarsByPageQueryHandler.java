package com.bist.backendmodule.modules.car.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.models.CarResponse;
import com.bist.backendmodule.modules.car.models.GetAllCarsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for handling the retrieval of all cars with pagination and sorting.
 */
@Service
public class GetAllCarsByPageQueryHandler implements Query<GetAllCarsRequest, CarResponse> {
    private final CarRepository carRepository;

    public GetAllCarsByPageQueryHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Executes the query to retrieve a paginated and sorted list of cars.
     *
     * @param getAllCarsRequest The request containing pagination, sorting, and specification details
     * @return The ResponseEntity containing the paginated list of cars
     */
    @Override
    public ResponseEntity<CarResponse> execute(GetAllCarsRequest getAllCarsRequest) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        if (getAllCarsRequest.getSortBy() != null && getAllCarsRequest.getSortDir() != null) {
            sort = Sort.by(Sort.Direction.fromString(getAllCarsRequest.getSortDir()), getAllCarsRequest.getSortBy());
        }

        Pageable pageable = PageRequest.of(getAllCarsRequest.getPageNo(), getAllCarsRequest.getPageSize(), sort);
        Page<Car> carPage = carRepository.findAll(getAllCarsRequest.getSpecification(), pageable);
        CarResponse carResponse = new CarResponse(carPage);
        return ResponseEntity.ok().body(carResponse);
    }
}
