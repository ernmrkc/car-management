package image.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.command.handlers.DeleteImageCommandHandler;
import com.bist.backendmodule.modules.image.models.Image;
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
public class DeleteImageCommandHandlerTest {
    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private DeleteImageCommandHandler deleteImageCommandHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(DeleteImageCommandHandlerTest.class);
    }

    /**
     * Test for successfully deleting an image with a valid ID.
     * Ensures the image is found and deleted from the repository.
     */
    @Test
    void deleteImage_validId_returnSuccess() {
        // Arrange
        Long imageId = 1L;
        Image image = new Image();
        image.setId(imageId);

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

        // Act
        ResponseEntity<Void> responseEntity = deleteImageCommandHandler.execute(imageId, null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(1)).delete(image);
    }

    /**
     * Test for attempting to delete an image with an invalid ID.
     * Ensures that an ImageNotFoundException is thrown.
     */
    @Test
    void deleteImage_invalidId_throwsImageNotFoundException() {
        // Arrange
        Long imageId = 1L;

        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ImageNotFoundException.class, () -> deleteImageCommandHandler.execute(imageId, null));
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(0)).delete(any(Image.class));
    }
}
