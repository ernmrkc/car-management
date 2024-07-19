package car.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.CarNotFoundException;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.command.handlers.DeleteCarCommandHandler;
import com.bist.backendmodule.modules.car.models.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class DeleteCarCommandHandlerTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private DeleteCarCommandHandler deleteCarCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(DeleteCarCommandHandlerTest.class);
    }

    /**
     * Tests the successful deletion of a car by ID.
     */
    @Test
    void deleteCar_validId_returnSuccess() {
        // Arrange
        Long carId = 1L;
        Car car = new Car();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        // Act
        ResponseEntity<Void> responseEntity = deleteCarCommandHandler.execute(carId, null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).delete(car);
    }

    /**
     * Tests the behavior when attempting to delete a car with an invalid ID.
     */
    @Test
    void deleteCar_invalidId_throwsCarNotFoundException() {
        // Arrange
        Long carId = 1L;

        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> deleteCarCommandHandler.execute(carId, null));
    }
}
