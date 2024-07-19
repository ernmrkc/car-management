package com.bist.backendmodule.modules.car.command.handlers;

import com.bist.backendmodule.exceptions.CarNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.query.handlers.GetBrandByIdQueryHandler;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.models.CarUpdateCommand;
import com.bist.backendmodule.modules.image.command.handlers.UpdateImageCommandHandler;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.query.handlers.GetImageByIdQueryHandler;
import com.bist.backendmodule.validations.CarCommandValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/**
 * Service for handling the update of Car entities.
 */
@Service
public class UpdateCarCommandHandler implements Command<CarUpdateCommand, BindingResult, Car> {
    private final CarRepository carRepository;
    private final CarCommandValidationService carCommandValidationService;
    private final GetBrandByIdQueryHandler getBrandByIdQueryHandler;
    private final GetImageByIdQueryHandler getImageByIdQueryHandler;
    private final UpdateImageCommandHandler updateImageCommandHandler;

    public UpdateCarCommandHandler(CarRepository carRepository,
                                   CarCommandValidationService carCommandValidationService,
                                   GetBrandByIdQueryHandler getBrandByIdQueryHandler,
                                   GetImageByIdQueryHandler getImageByIdQueryHandler,
                                   UpdateImageCommandHandler updateImageCommandHandler) {
        this.carRepository = carRepository;
        this.carCommandValidationService = carCommandValidationService;
        this.getBrandByIdQueryHandler = getBrandByIdQueryHandler;
        this.getImageByIdQueryHandler = getImageByIdQueryHandler;
        this.updateImageCommandHandler = updateImageCommandHandler;
    }

    /**
     * Executes the car update command.
     *
     * @param carUpdateCommand The car update command containing the updated car details
     * @param bindingResult    The binding result for validation errors
     * @return ResponseEntity containing the updated car
     * @throws CarNotFoundException If the car with the specified ID is not found.
     */
    @Override
    public ResponseEntity<Car> execute(CarUpdateCommand carUpdateCommand, BindingResult bindingResult) {
        // Validate CarCommand
        carCommandValidationService.validateCarCommand(carUpdateCommand.getCarCommand(), bindingResult, UpdateCarCommandHandler.class);

        Optional<Car> carOptional = carRepository.findById(carUpdateCommand.getCarId());
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException(UpdateCarCommandHandler.class);
        }

        // Set image id
        carUpdateCommand.getUpdateImageCommand().setImageId(carOptional.get().getImage().getId());

        // Update image
        updateImageCommandHandler.execute(carUpdateCommand.getUpdateImageCommand(), null);

        Car car = new Car(carUpdateCommand.getCarCommand());
        car.setId(carUpdateCommand.getCarId());

        Brand brand = getBrandByIdQueryHandler.execute(carUpdateCommand.getCarCommand().getBrandId()).getBody();
        car.setBrand(brand);

        Image image = getImageByIdQueryHandler.execute(carUpdateCommand.getUpdateImageCommand().getImageId()).getBody();
        car.setImage(image);

        carRepository.save(car);
        return ResponseEntity.ok().body(car);
    }
}
