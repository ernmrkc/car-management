package com.bist.backendmodule.modules.car.command.handlers;

import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.query.handlers.GetBrandByIdQueryHandler;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.models.CarCommand;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.query.handlers.GetImageByIdQueryHandler;
import com.bist.backendmodule.validations.CarCommandValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * Service for handling the creation of Car entities.
 */
@Service
public class CreateCarCommandHandler implements Command<CarCommand, BindingResult, Car> {
    private final CarRepository carRepository;
    private final GetBrandByIdQueryHandler getBrandByIdQueryHandler;
    private final GetImageByIdQueryHandler getImageByIdQueryHandler;
    private final CarCommandValidationService carCommandValidationService;

    public CreateCarCommandHandler(CarRepository carRepository,
                                   GetBrandByIdQueryHandler getBrandByIdQueryHandler,
                                   GetImageByIdQueryHandler getImageByIdQueryHandler,
                                   CarCommandValidationService carCommandValidationService) {
        this.carRepository = carRepository;
        this.getBrandByIdQueryHandler = getBrandByIdQueryHandler;
        this.getImageByIdQueryHandler = getImageByIdQueryHandler;
        this.carCommandValidationService = carCommandValidationService;
    }

    /**
     * Executes the car creation command.
     *
     * @param carCommand    The car command containing the data to create the car
     * @param bindingResult The binding result to hold validation errors
     * @return ResponseEntity containing the created car
     */
    @Override
    public ResponseEntity<Car> execute(CarCommand carCommand, BindingResult bindingResult) {
        // Validate CarCommand
        carCommandValidationService.validateCarCommand(carCommand, bindingResult, CreateCarCommandHandler.class);

        Car car = new Car(carCommand);

        Brand brand = getBrandByIdQueryHandler.execute(carCommand.getBrandId()).getBody();
        car.setBrand(brand);

        Image image = getImageByIdQueryHandler.execute(carCommand.getImageId()).getBody();
        car.setImage(image);

        carRepository.save(car);
        return ResponseEntity.ok().body(car);
    }
}
