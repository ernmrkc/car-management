package brand.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.BrandAlreadyExistsException;
import com.bist.backendmodule.exceptions.BrandNotValidException;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.command.handlers.CreateBrandCommandHandler;
import com.bist.backendmodule.modules.brand.models.Brand;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class CreateBrandCommandHandlerTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandValidationService brandValidationService;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private CreateBrandCommandHandler createBrandCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(CreateBrandCommandHandlerTest.class);
    }

    /**
     * Test for successful creation of a brand.
     * It verifies that when a valid brand is provided, the response status is OK.
     */
    @Test
    void createBrand_validBrand_returnSuccess() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("TestBrand");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> responseEntity = createBrandCommandHandler.execute(brand, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(brandValidationService, times(1)).validateBrand(any(Brand.class), any(BindingResult.class), any(Class.class));
        verify(brandRepository, times(1)).findByName(anyString());
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    /**
     * Test for invalid brand creation.
     * It verifies that when an invalid brand is provided, a BrandNotValidException is thrown.
     */
    @Test
    void createBrand_invalidBrand_throwsBrandNotValidException() {
        // Arrange
        Brand brand = new Brand();

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new ObjectError("brand", "Invalid brand")));

        doThrow(new BrandNotValidException("Invalid brand", CreateBrandCommandHandler.class))
                .when(brandValidationService).validateBrand(any(Brand.class), any(BindingResult.class), any(Class.class));

        // Act & Assert
        assertThrows(BrandNotValidException.class, () -> createBrandCommandHandler.execute(brand, bindingResult));
    }

    /**
     * Test for brand creation when the brand already exists.
     * It verifies that when a brand with the same name already exists, a BrandAlreadyExistsException is thrown.
     */
    @Test
    void createBrand_whenBrandAlreadyExists_throwsBrandAlreadyExistsException(){
        // Arrange
        Brand brand = new Brand();
        brand.setName("ExistingBrand");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(brandRepository.findByName("ExistingBrand")).thenReturn(Optional.of(brand));

        // Act & Assert
        assertThrows(BrandAlreadyExistsException.class, () -> createBrandCommandHandler.execute(brand, bindingResult));
    }
}
