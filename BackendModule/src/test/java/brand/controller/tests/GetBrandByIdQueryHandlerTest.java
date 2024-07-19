package brand.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.query.handlers.GetBrandByIdQueryHandler;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendModuleApplication.class)
public class GetBrandByIdQueryHandlerTest {
    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private GetBrandByIdQueryHandler getBrandByIdQueryHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GetBrandByIdQueryHandlerTest.class);
    }

    /**
     * Tests the successful retrieval of a brand by a valid ID.
     */
    @Test
    void getBrandById_validId_returnBrand() {
        // Arrange
        Long brandId = 1L;
        Brand brand = new Brand();

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // Act
        ResponseEntity<Brand> responseEntity = getBrandByIdQueryHandler.execute(brandId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(brand, responseEntity.getBody());
    }

    /**
     * Tests the behavior when an invalid ID is provided, expecting a BrandNotFoundException.
     */
    @Test
    void getBrandById_invalidId_throwsBrandNotFoundException() {
        // Arrange
        Long brandId = 1L;

        when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BrandNotFoundException.class, () -> getBrandByIdQueryHandler.execute(brandId));
    }
}
