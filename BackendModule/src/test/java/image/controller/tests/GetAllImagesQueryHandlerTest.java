package image.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.query.handlers.GetAllImagesQueryHandler;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class GetAllImagesQueryHandlerTest {
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private GetAllImagesQueryHandler getAllImagesQueryHandler;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GetAllImagesQueryHandlerTest.class);
    }

    /**
     * Test for successfully retrieving all images.
     * Ensures the repository returns the expected list of images.
     */
    @Test
    void getAllImages_returnImageList() {
        // Arrange
        Image image1 = new Image();
        image1.setId(1L);
        image1.setFilename("image1.jpg");

        Image image2 = new Image();
        image2.setId(2L);
        image2.setFilename("image2.jpg");

        List<Image> imageList = Arrays.asList(image1, image2);

        when(imageRepository.findAll()).thenReturn(imageList);

        // Act
        ResponseEntity<List<Image>> responseEntity = getAllImagesQueryHandler.execute(null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        verify(imageRepository, times(1)).findAll();
    }

    /**
     * Test for handling no images found in the repository.
     * Ensures the repository returns an empty list when no images are found.
     */
    @Test
    void getAllImages_noImagesFound_returnEmptyList() {
        // Arrange
        when(imageRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Image>> responseEntity = getAllImagesQueryHandler.execute(null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(imageRepository, times(1)).findAll();
    }
}
