package car.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.modules.car.CarRepository;
import com.bist.backendmodule.modules.car.models.Car;
import com.bist.backendmodule.modules.car.models.CarResponse;
import com.bist.backendmodule.modules.car.models.GetAllCarsRequest;
import com.bist.backendmodule.modules.car.query.handlers.GetAllCarsByPageQueryHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class GetAllCarsByPageQueryHandlerTest {
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private GetAllCarsByPageQueryHandler getAllCarsByPageQueryHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GetAllCarsByPageQueryHandlerTest.class);
    }

    /**
     * Test case for retrieving all cars by page with valid parameters.
     * Validates that the service correctly retrieves paginated car data.
     */
    @Test
    void getAllCarsByPage_validParameters_returnCarResponse() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String sortDir = "asc";
        Specification<Car> spec = Specification.where(null);

        List<Car> cars = Arrays.asList(new Car(), new Car());
        Page<Car> carPage = new PageImpl<>(cars);

        GetAllCarsRequest getAllCarsRequest = new GetAllCarsRequest(spec, pageSize, pageNo, sortBy, sortDir);

        when(carRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(carPage);

        // Act
        ResponseEntity<CarResponse> responseEntity = getAllCarsByPageQueryHandler.execute(getAllCarsRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().getContent().size());
        verify(carRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    /**
     * Test case for retrieving all cars by page with empty result.
     * Validates that the service correctly handles cases where no cars are found.
     */
    @Test
    void getAllCarsByPage_noCarsFound_returnEmptyCarResponse() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String sortDir = "asc";
        Specification<Car> spec = Specification.where(null);

        Page<Car> carPage = new PageImpl<>(Collections.emptyList());

        GetAllCarsRequest getAllCarsRequest = new GetAllCarsRequest(spec, pageSize, pageNo, sortBy, sortDir);

        when(carRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(carPage);

        // Act
        ResponseEntity<CarResponse> responseEntity = getAllCarsByPageQueryHandler.execute(getAllCarsRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, responseEntity.getBody().getContent().size());
        verify(carRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
