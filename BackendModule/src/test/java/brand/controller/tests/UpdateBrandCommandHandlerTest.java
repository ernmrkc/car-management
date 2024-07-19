package brand.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.BrandAlreadyExistsException;
import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.exceptions.BrandNotValidException;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.command.handlers.UpdateBrandCommandHandler;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.models.UpdateBrandCommand;
import com.bist.backendmodule.validations.BrandValidationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = BackendModuleApplication.class)
public class UpdateBrandCommandHandlerTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandValidationService brandValidationService;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private UpdateBrandCommandHandler updateBrandCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(UpdateBrandCommandHandlerTest.class);
    }

    /**
     * Test for successful update of a brand.
     * It verifies that when a valid brand update command is provided, the response status is OK.
     */
    @Test
    void updateBrand_validCommand_returnSuccess() {
        // Arrange
        Long brandId = 1L;
        Brand existingBrand = new Brand();

        Brand updatedBrand = new Brand();
        updatedBrand.setName("NewBrandName");

        UpdateBrandCommand updateBrandCommand = new UpdateBrandCommand(brandId, updatedBrand);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));
        when(bindingResult.hasErrors()).thenReturn(false);
        when(brandRepository.findByName("NewBrandName")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Brand> responseEntity = updateBrandCommandHandler.execute(updateBrandCommand, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(brandValidationService, times(1)).validateBrand(any(Brand.class), any(BindingResult.class), any(Class.class));
        verify(brandRepository, times(1)).findByName(anyString());
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    /**
     * Test for updating a brand when the brand ID is not found.
     * It verifies that when a non-existent brand ID is provided, a BrandNotFoundException is thrown.
     */
    @Test
    void updateBrand_whenBrandNotFound_throwsBrandNotFoundException() {
        // Arrange
        Long brandId = 1L;
        Brand updatedBrand = new Brand();
        updatedBrand.setName("NewBrandName");

        UpdateBrandCommand updateBrandCommand = new UpdateBrandCommand(brandId, updatedBrand);

        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BrandNotFoundException.class, () -> updateBrandCommandHandler.execute(updateBrandCommand, bindingResult));
    }

    /**
     * Test for updating a brand when the updated brand name already exists.
     * It verifies that when an updated brand name already exists, a BrandAlreadyExistsException is thrown.
     */
    @Test
    void updateBrand_whenBrandNameAlreadyExists_throwsBrandAlreadyExistsException() {
        // Arrange
        Long brandId = 1L;
        Brand existingBrand = new Brand();

        Brand anotherBrand = new Brand();
        anotherBrand.setId(2L);
        anotherBrand.setName("NewBrandName");

        Brand updatedBrand = new Brand();
        updatedBrand.setName("NewBrandName");

        UpdateBrandCommand updateBrandCommand = new UpdateBrandCommand(brandId, updatedBrand);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));
        when(bindingResult.hasErrors()).thenReturn(false);
        when(brandRepository.findByName("NewBrandName")).thenReturn(Optional.of(anotherBrand));

        // Act & Assert
        assertThrows(BrandAlreadyExistsException.class, () -> updateBrandCommandHandler.execute(updateBrandCommand, bindingResult));
    }

    /**
     * Test for updating a brand with invalid data.
     * It verifies that when invalid data is provided, a BrandNotValidException is thrown.
     */
    @Test
    void updateBrand_invalidData_throwsBrandNotValidException() {
        // Arrange
        Long brandId = 1L;
        Brand existingBrand = new Brand();

        Brand updatedBrand = new Brand();
        updatedBrand.setName(""); // Invalid name

        UpdateBrandCommand updateBrandCommand = new UpdateBrandCommand(brandId, updatedBrand);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new ObjectError("brand", "Invalid brand")));

        doThrow(new BrandNotValidException("Invalid brand", UpdateBrandCommandHandler.class))
                .when(brandValidationService).validateBrand(any(Brand.class), any(BindingResult.class), any(Class.class));

        // Act & Assert
        assertThrows(BrandNotValidException.class, () -> updateBrandCommandHandler.execute(updateBrandCommand, bindingResult));
    }
}
