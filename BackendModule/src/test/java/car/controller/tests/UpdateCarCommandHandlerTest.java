package car.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.CarNotFoundException;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.query.handlers.GetBrandByIdQueryHandler;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.command.handlers.UpdateCarCommandHandler;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.models.CarCommand;
import com.bist.backendmodule.modules.car.models.CarUpdateCommand;
import com.bist.backendmodule.modules.image.command.handlers.UpdateImageCommandHandler;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.models.UpdateImageCommand;
import com.bist.backendmodule.modules.image.query.handlers.GetImageByIdQueryHandler;
import com.bist.backendmodule.validations.CarCommandValidationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class UpdateCarCommandHandlerTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarCommandValidationService carCommandValidationService;
    @Mock
    private GetBrandByIdQueryHandler getBrandByIdQueryHandler;
    @Mock
    private GetImageByIdQueryHandler getImageByIdQueryHandler;
    @Mock
    private UpdateImageCommandHandler updateImageCommandHandler;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private UpdateCarCommandHandler updateCarCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(UpdateCarCommandHandlerTest.class);
    }

    /**
     * Test case for successfully updating a car.
     * Validates that the service correctly updates the car when provided with a valid CarUpdateCommand.
     *
     * @throws IOException if there is an error during file handling
     */
    @Test
    void updateCar_validCommand_returnSuccess() throws IOException {
        // Arrange
        Long carId = 1L;
        Car existingCar = new Car();
        Image existingImage = new Image();
        existingCar.setImage(existingImage);

        CarCommand carCommand = new CarCommand();
        carCommand.setBrandId(1L);
        carCommand.setImageId(1L);
        carCommand.setSpecification("Updated Specification");
        carCommand.setEngineLiter(2.5f);
        carCommand.setIsNew(true);
        carCommand.setPrice(new BigDecimal("25000"));
        carCommand.setReleaseDateTime(LocalDateTime.now());

        Brand brand = new Brand();
        Image image = new Image();

        UpdateImageCommand updateImageCommand = new UpdateImageCommand();
        updateImageCommand.setImageId(existingCar.getImage().getId());

        MultipartFile file = mock(MultipartFile.class);
        updateImageCommand.setFile(file);

        CarUpdateCommand carUpdateCommand = new CarUpdateCommand(carId, carCommand, updateImageCommand);

        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));
        when(bindingResult.hasErrors()).thenReturn(false);
        when(getBrandByIdQueryHandler.execute(carCommand.getBrandId())).thenReturn(ResponseEntity.ok().body(brand));
        when(getImageByIdQueryHandler.execute(updateImageCommand.getImageId())).thenReturn(ResponseEntity.ok().body(image));
        when(updateImageCommandHandler.execute(updateImageCommand, null)).thenReturn(ResponseEntity.ok().body(existingImage));

        // Act
        ResponseEntity<Car> responseEntity = updateCarCommandHandler.execute(carUpdateCommand, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(carCommandValidationService, times(1)).validateCarCommand(any(CarCommand.class), any(BindingResult.class), any(Class.class));
        verify(carRepository, times(1)).findById(carId);
        verify(updateImageCommandHandler, times(1)).execute(any(UpdateImageCommand.class), eq(null));
        verify(carRepository, times(1)).save(any(Car.class));
    }

    /**
     * Test case for attempting to update a car that does not exist.
     * Validates that the service throws CarNotFoundException when the car is not found.
     */
    @Test
    void updateCar_whenCarNotFound_throwsCarNotFoundException() {
        // Arrange
        Long carId = 1L;
        CarCommand carCommand = new CarCommand();
        UpdateImageCommand updateImageCommand = new UpdateImageCommand(1L, mock(MultipartFile.class));
        CarUpdateCommand carUpdateCommand = new CarUpdateCommand(carId, carCommand, updateImageCommand);

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> updateCarCommandHandler.execute(carUpdateCommand, bindingResult));
        verify(carRepository, times(1)).findById(carId);
    }
}
