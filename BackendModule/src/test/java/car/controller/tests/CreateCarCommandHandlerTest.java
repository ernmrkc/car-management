package car.controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.exceptions.CarCommandNotValidException;
import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.brand.query.handlers.GetBrandByIdQueryHandler;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.image.query.handlers.GetImageByIdQueryHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.bist.backendmodule.modules.car.command.handlers.CreateCarCommandHandler;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.models.CarCommand;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.validations.CarCommandValidationService;
import org.springframework.validation.ObjectError;

@SpringBootTest(classes = BackendModuleApplication.class)
public class CreateCarCommandHandlerTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private GetBrandByIdQueryHandler getBrandByIdQueryHandler;
    @Mock
    private GetImageByIdQueryHandler getImageByIdQueryHandler;
    @Mock
    private CarCommandValidationService carCommandValidationService;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CreateCarCommandHandler createCarCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(CreateCarCommandHandlerTest.class);
    }

    /**
     * Tests the successful creation of a car.
     */
    @Test
    void createCar_validCarCommand_returnSuccess() {
        // Arrange
        CarCommand carCommand = new CarCommand();
        carCommand.setBrandId(1L);
        carCommand.setImageId(1L);
        carCommand.setSpecification("Test Specification");
        carCommand.setEngineLiter(2.0f);
        carCommand.setIsNew(true);
        carCommand.setPrice(new BigDecimal("20000"));
        carCommand.setReleaseDateTime(LocalDateTime.now());

        Brand brand = new Brand();
        Image image = new Image();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(getBrandByIdQueryHandler.execute(carCommand.getBrandId())).thenReturn(ResponseEntity.ok().body(brand));
        when(getImageByIdQueryHandler.execute(carCommand.getImageId())).thenReturn(ResponseEntity.ok().body(image));
        when(carRepository.save(any(Car.class))).thenReturn(new Car(carCommand));

        // Act
        ResponseEntity<Car> responseEntity = createCarCommandHandler.execute(carCommand, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(getBrandByIdQueryHandler, times(1)).execute(carCommand.getBrandId());
        verify(getImageByIdQueryHandler, times(1)).execute(carCommand.getImageId());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    /**
     * Tests the behavior when validation fails for a car command.
     */
    @Test
    void createCar_invalidCarCommand_throwsCarCommandNotValidException() {
        // Arrange
        CarCommand carCommand = new CarCommand();

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new ObjectError("carCommand", "Invalid car command")));

        doThrow(new CarCommandNotValidException("Invalid car command", CreateCarCommandHandler.class))
                .when(carCommandValidationService).validateCarCommand(any(CarCommand.class), any(BindingResult.class), any(Class.class));

        // Act & Assert
        assertThrows(CarCommandNotValidException.class, () -> createCarCommandHandler.execute(carCommand, bindingResult));
    }

    /**
     * Tests the behavior when the specified brand ID does not exist.
     */
    @Test
    void createCar_brandNotFound_throwsBrandNotFoundException() {
        // Arrange
        CarCommand carCommand = new CarCommand();
        carCommand.setBrandId(1L);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(getBrandByIdQueryHandler.execute(carCommand.getBrandId())).thenThrow(new BrandNotFoundException(CreateCarCommandHandler.class));

        // Act & Assert
        assertThrows(BrandNotFoundException.class, () -> createCarCommandHandler.execute(carCommand, bindingResult));
    }

    /**
     * Tests the behavior when the specified image ID does not exist.
     */
    @Test
    void createCar_imageNotFound_throwsImageNotFoundException() {
        // Arrange
        CarCommand carCommand = new CarCommand();
        carCommand.setBrandId(1L);
        carCommand.setImageId(1L);

        Brand brand = new Brand();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(getBrandByIdQueryHandler.execute(carCommand.getBrandId())).thenReturn(ResponseEntity.ok().body(brand));
        when(getImageByIdQueryHandler.execute(carCommand.getImageId())).thenThrow(new ImageNotFoundException(CreateCarCommandHandler.class));

        // Act & Assert
        assertThrows(ImageNotFoundException.class, () -> createCarCommandHandler.execute(carCommand, bindingResult));
    }
}
