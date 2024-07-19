package brand.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.query.handlers.GetAllBrandsQueryHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendModuleApplication.class)
public class GetAllBrandsQueryHandlerTest {

    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private GetAllBrandsQueryHandler getAllBrandsQueryHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GetAllBrandsQueryHandlerTest.class);
    }

    /**
     * Test for retrieving all brands successfully.
     * It verifies that when the repository returns a list of brands, the response status is OK and the list is returned correctly.
     */
    @Test
    void getAllBrands_returnBrandsList() {
        // Arrange
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1L, "Brand1", null));
        brands.add(new Brand(2L, "Brand2", null));

        when(brandRepository.findAll()).thenReturn(brands);

        // Act
        ResponseEntity<List<Brand>> responseEntity = getAllBrandsQueryHandler.execute(null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
        assertEquals("Brand1", responseEntity.getBody().get(0).getName());
        assertEquals("Brand2", responseEntity.getBody().get(1).getName());
    }

    /**
     * Test for retrieving all brands when there are no brands.
     * It verifies that when the repository returns an empty list, the response status is OK and an empty list is returned.
     */
    @Test
    void getAllBrands_whenNoBrands_returnEmptyList() {
        // Arrange
        List<Brand> brands = new ArrayList<>();

        when(brandRepository.findAll()).thenReturn(brands);

        // Act
        ResponseEntity<List<Brand>> responseEntity = getAllBrandsQueryHandler.execute(null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
    }
}