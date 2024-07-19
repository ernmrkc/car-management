package image.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.query.handlers.GetImageByIdQueryHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class GetImagesByIdQueryHandlerTest {
    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private GetImageByIdQueryHandler getImageByIdQueryHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GetImagesByIdQueryHandlerTest.class);
    }

    /**
     * Test for successfully retrieving an image by ID.
     * Ensures the repository returns the expected image.
     */
    @Test
    void getImageById_validId_returnImage() {
        // Arrange
        Long imageId = 1L;
        Image image = new Image();
        image.setId(imageId);
        image.setFilename("testImage.jpg");

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

        // Act
        ResponseEntity<Image> responseEntity = getImageByIdQueryHandler.execute(imageId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(image, responseEntity.getBody());
        verify(imageRepository, times(1)).findById(imageId);
    }

    /**
     * Test for handling non-existing image by ID.
     * Ensures the handler throws ImageNotFoundException when image is not found.
     */
    @Test
    void getImageById_invalidId_throwsImageNotFoundException() {
        // Arrange
        Long imageId = 1L;

        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ImageNotFoundException.class, () -> getImageByIdQueryHandler.execute(imageId));
        verify(imageRepository, times(1)).findById(imageId);
    }

}
