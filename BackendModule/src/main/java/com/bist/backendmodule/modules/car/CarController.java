package com.bist.backendmodule.modules.car;

import com.bist.backendmodule.modules.car.command.handlers.CreateCarCommandHandler;
import com.bist.backendmodule.modules.car.command.handlers.DeleteCarCommandHandler;
import com.bist.backendmodule.modules.car.command.handlers.UpdateCarCommandHandler;
import com.bist.backendmodule.modules.car.models.*;
import com.bist.backendmodule.modules.car.query.handlers.GetAllCarsByPageQueryHandler;
import com.bist.backendmodule.modules.car.query.handlers.GetAllCarsQueryHandler;
import com.bist.backendmodule.modules.image.command.handlers.CreateImageCommandHandler;
import com.bist.backendmodule.modules.image.models.ImageDTO;
import com.bist.backendmodule.modules.image.models.UpdateImageCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * REST controller for handling car-related operations.
 */
@RestController
@RequestMapping("/car")
@Tag(name = "Car Controller", description = "Operations related to cars")
public class CarController {
    private final CreateCarCommandHandler createCarCommandHandler;
    private final CreateImageCommandHandler createImageCommandHandler;
    private final DeleteCarCommandHandler deleteCarCommandHandler;
    private final UpdateCarCommandHandler updateCarCommandHandler;
    private final GetAllCarsByPageQueryHandler getAllCarsByPageQueryHandler;
    private final GetAllCarsQueryHandler getAllCarsQueryHandler;


    public CarController(CreateCarCommandHandler createCarCommandHandler,
                         CreateImageCommandHandler createImageCommandHandler,
                         DeleteCarCommandHandler deleteCarCommandHandler,
                         UpdateCarCommandHandler updateCarCommandHandler,
                         GetAllCarsByPageQueryHandler getAllCarsByPageQueryHandler,
                         GetAllCarsQueryHandler getAllCarsQueryHandler) {
        this.createCarCommandHandler = createCarCommandHandler;
        this.createImageCommandHandler = createImageCommandHandler;
        this.deleteCarCommandHandler = deleteCarCommandHandler;
        this.updateCarCommandHandler = updateCarCommandHandler;
        this.getAllCarsByPageQueryHandler = getAllCarsByPageQueryHandler;
        this.getAllCarsQueryHandler = getAllCarsQueryHandler;
    }

    /**
     * Creates a new car.
     *
     * @param carCommand    The car command object
     * @param bindingResult The binding result
     * @param file          The image file
     * @return The Response Entity with the created car
     */
    @PreAuthorize("hasAuthority('PERMISSON_CREATE_CAR')")
    @PostMapping
    @Operation(summary = "Create a new car", description = "Creates a new car with the given details.")
    public ResponseEntity<Car> creteCar(@Valid @RequestPart("car") CarCommand carCommand, BindingResult bindingResult, @RequestPart("file") MultipartFile file) {
        ImageDTO imageDTO = createImageCommandHandler.execute(file, null).getBody();
        carCommand.setImageId(Objects.requireNonNull(imageDTO).getImageId());
        return createCarCommandHandler.execute(carCommand, bindingResult);
    }

    /**
     * Retrieves all cars.
     *
     * @return The response entity with the list of cars
     */
    @PreAuthorize("hasAuthority('PERMISSON_GET_CAR')")
    @GetMapping
    @Operation(summary = "Get all cars", description = "Retrieve a list of all cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return getAllCarsQueryHandler.execute(null);
    }

    /**
     * Retrieves all cars by page with filtering and sorting options.
     *
     * @param brand         The brand filter
     * @param specification The specification filter
     * @param engineLiter   The engine liter filter
     * @param isNew         The new status filter
     * @param minPrice      The minimum price filter
     * @param maxPrice      The maximum price filter
     * @param minDate       The minimum release date filter
     * @param maxDate       The maximum release date filter
     * @param sortBy        The sorting field
     * @param sortDir       The sorting direction
     * @param searchTerm    The search term
     * @param pageNo        The page number
     * @param pageSize      The page size
     * @return The response entity with the paginated car response
     */
    @PreAuthorize("hasAuthority('PERMISSON_GET_CAR')")
    @GetMapping("/byPage")
    @Operation(summary = "Get cars by page", description = "Retrieve a list of cars by page with optional filters.")
    public ResponseEntity<CarResponse> getAllCarsByPage(@RequestParam(value = "brand", required = false) String brand,
                                                        @RequestParam(value = "specification", required = false) String specification,
                                                        @RequestParam(value = "engineLiter", required = false) Float engineLiter,
                                                        @RequestParam(value = "isNew", required = false) Boolean isNew,
                                                        @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                                                        @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                                                        @RequestParam(value = "minDate", required = false) LocalDateTime minDate,
                                                        @RequestParam(value = "maxDate", required = false) LocalDateTime maxDate,
                                                        @RequestParam(value = "sortBy", required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", required = false) String sortDir,
                                                        @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                        @RequestParam(value = "pageNo") int pageNo,
                                                        @RequestParam(value = "pageSize") int pageSize) {

        Specification<Car> spec = Specification.where(null);

        if (brand != null) spec = spec.and(CarSpecification.hasBrand(brand));
        if (specification != null) spec = spec.and(CarSpecification.hasSpecification(specification));
        if (engineLiter != null) spec = spec.and(CarSpecification.hasEngineLiter(engineLiter));
        if (isNew != null) spec = spec.and(CarSpecification.isNew(isNew));
        if (minPrice != null) spec = spec.and(CarSpecification.hasPriceGreaterThanOrEqual(minPrice));
        if (maxPrice != null) spec = spec.and(CarSpecification.hasPriceLessThanOrEqual(maxPrice));
        if (minDate != null) spec = spec.and(CarSpecification.hasReleaseDateTimeAfter(minDate));
        if (maxDate != null) spec = spec.and(CarSpecification.hasReleaseDateTimeBefore(maxDate));
        if (searchTerm != null) spec = spec.and(CarSpecification.hasSearchTerm(searchTerm));


        GetAllCarsRequest getAllCarsRequest = new GetAllCarsRequest(spec, pageSize, pageNo, sortBy, sortDir);
        return getAllCarsByPageQueryHandler.execute(getAllCarsRequest);
    }

    /**
     * Updates an existing car.
     *
     * @param carCommand    The car command object
     * @param bindingResult The binding result
     * @param file          The image file
     * @param carId         The ID of the car to update
     * @return The response entity with the updated car
     */
    @PreAuthorize("hasAuthority('PERMISSON_EDIT_CAR')")
    @PutMapping("/{carId}")
    @Operation(summary = "Update a car", description = "Updates an existing car with the given details.")
    public ResponseEntity<Car> updateCar(@Valid @RequestPart("car") CarCommand carCommand, BindingResult bindingResult, @RequestPart("file") MultipartFile file, @PathVariable Long carId) {
        UpdateImageCommand updateImageCommand = new UpdateImageCommand();
        updateImageCommand.setFile(file);

        CarUpdateCommand carUpdateCommand = new CarUpdateCommand(carId, carCommand, updateImageCommand);

        return updateCarCommandHandler.execute(carUpdateCommand, bindingResult);
    }

    /**
     * Deletes a car.
     *
     * @param id The ID of the car to delete
     * @return The response entity indicating the result
     */
    @PreAuthorize("hasAuthority('PERMISSON_DELETE_CAR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a car", description = "Deletes an existing car by ID.")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        return deleteCarCommandHandler.execute(id, null);
    }
}