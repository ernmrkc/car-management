package brand.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.command.handlers.DeleteBrandCommandHandler;
import com.bist.backendmodule.modules.brand.models.Brand;
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
public class DeleteBrandCommandHandlerTest {
    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private DeleteBrandCommandHandler deleteBrandCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(DeleteBrandCommandHandlerTest.class);
    }

    /**
     * Tests successful deletion of a brand when a valid ID is provided.
     */
    @Test
    void deleteBrand_validId_returnSuccess(){
        // Arrange
        Long validId = 1L;
        Brand brand = new Brand();

        when(brandRepository.findById(validId)).thenReturn(Optional.of(brand));

        // Act
        ResponseEntity<Void> responseEntity = deleteBrandCommandHandler.execute(validId, null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(brandRepository, times(1)).findById(1L);
        verify(brandRepository, times(1)).delete(brand);
    }

    /**
     * Tests that an exception is thrown when trying to delete a brand with an invalid ID.
     */
    @Test
    void deleteBrand_invalidId_throwsBrandNotFoundException() {
        // Arrange
        Long invalidId = 1L;
        when(brandRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BrandNotFoundException.class, () -> deleteBrandCommandHandler.execute(invalidId, null));
    }

}
