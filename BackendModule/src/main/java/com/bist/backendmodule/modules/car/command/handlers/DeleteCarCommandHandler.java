package com.bist.backendmodule.modules.car.command.handlers;

import com.bist.backendmodule.exceptions.CarNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling the deletion of Car entities.
 */
@Service
public class DeleteCarCommandHandler implements Command<Long, Void, Void> {
    private final CarRepository carRepository;

    public DeleteCarCommandHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Executes the car deletion command.
     *
     * @param id            The ID of the car to delete
     * @param bindingResult Not used in this service
     * @return Void ResponseEntity
     * @throws CarNotFoundException If the car with the specified ID is not found.
     */
    @Override
    public ResponseEntity<Void> execute(Long id, Void bindingResult) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException(DeleteCarCommandHandler.class);
        }
        Car car = carOptional.get();
        carRepository.delete(car);
        return ResponseEntity.ok().build();
    }
}
