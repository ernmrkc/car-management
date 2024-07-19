package car.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.query.handlers.GetAllCarsQueryHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class GetAllCarsQueryHandlerTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private GetAllCarsQueryHandler getAllCarsQueryHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GetAllCarsQueryHandlerTest.class);
    }

    /**
     * Test case for retrieving all cars successfully.
     * Validates that the service correctly retrieves all car data.
     */
    @Test
    void getAllCars_success_returnCarList() {
        // Arrange
        List<Car> cars = Arrays.asList(new Car(), new Car());

        when(carRepository.findAll()).thenReturn(cars);

        // Act
        ResponseEntity<List<Car>> responseEntity = getAllCarsQueryHandler.execute(null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        verify(carRepository, times(1)).findAll();
    }

    /**
     * Test case for retrieving all cars when no cars are found.
     * Validates that the service correctly handles cases where no cars are found.
     */
    @Test
    void getAllCars_noCarsFound_returnEmptyList() {
        // Arrange
        when(carRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Car>> responseEntity = getAllCarsQueryHandler.execute(null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, responseEntity.getBody().size());
        verify(carRepository, times(1)).findAll();
    }
}
