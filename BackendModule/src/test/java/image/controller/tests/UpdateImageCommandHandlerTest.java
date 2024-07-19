package image.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.FileEmptyException;
import com.bist.backendmodule.exceptions.FileUploadException;
import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.command.handlers.UpdateImageCommandHandler;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.models.UpdateImageCommand;
import com.bist.backendmodule.validations.ImageValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class UpdateImageCommandHandlerTest {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageValidationService imageValidationService;
    @InjectMocks
    private UpdateImageCommandHandler updateImageCommandHandler;
    private final String uploadDirectory = "src/main/resources/static/uploads/";
    private MockMultipartFile mockMultipartFile;

    /**
     * Set up method to initialize the mocks and create a mock multipart file.
     * This method runs before each test.
     */
    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Initialize the command handler with the mock repository and validation service
        updateImageCommandHandler = new UpdateImageCommandHandler(imageRepository, imageValidationService);
        updateImageCommandHandler.setUploadDirectory(uploadDirectory);

        // Create a mock multipart file from a test resource
        Path resourcePath = Paths.get("src/test/resources/test-image.jpg");
        mockMultipartFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                Files.readAllBytes(resourcePath)
        );

        // Ensure the upload directory exists
        Files.createDirectories(Paths.get(uploadDirectory));
    }


    /**
     * Test for successfully updating an image with a valid command.
     * Ensures the image is found, updated, and saved in the repository.
     */
    @Test
    void updateImage_validCommand_returnSuccess(){
        // Arrange
        Long imageId = 1L;
        Image existingImage = new Image();
        existingImage.setId(imageId);
        existingImage.setFilename("oldImage.jpg");
        existingImage.setFullPath(uploadDirectory + "oldImage.jpg");

        String fileName = mockMultipartFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, fileName);

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(existingImage));
        when(imageRepository.save(any(Image.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UpdateImageCommand updateImageCommand = new UpdateImageCommand(imageId, mockMultipartFile);
        ResponseEntity<Image> responseEntity = updateImageCommandHandler.execute(updateImageCommand, null);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(fileName, responseEntity.getBody().getFilename());
        assertEquals(filePath.toAbsolutePath().toString(), responseEntity.getBody().getFullPath());

        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(1)).save(any(Image.class));
        verify(imageValidationService, times(1)).validateImage(any(Image.class), any(BindingResult.class), any(Class.class));
    }

    /**
     * Test for updating an image with an invalid ID.
     * Ensures that an ImageNotFoundException is thrown.
     */
    @Test
    void updateImage_invalidId_throwsImageNotFoundException() {
        // Arrange
        Long imageId = 1L;

        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        UpdateImageCommand updateImageCommand = new UpdateImageCommand(imageId, mockMultipartFile);

        // Act & Assert
        assertThrows(ImageNotFoundException.class, () -> updateImageCommandHandler.execute(updateImageCommand, null));
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(0)).save(any(Image.class));
    }

    /**
     * Test for updating an image with an empty file.
     * Ensures that a FileEmptyException is thrown.
     */
    @Test
    void updateImage_emptyFile_throwsFileEmptyException() {
        // Arrange
        Long imageId = 1L;
        Image existingImage = new Image();
        existingImage.setId(imageId);

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(existingImage));

        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "empty.jpg",
                "image/jpeg",
                new byte[0]
        );

        UpdateImageCommand updateImageCommand = new UpdateImageCommand(imageId, emptyFile);

        // Act & Assert
        assertThrows(FileEmptyException.class, () -> updateImageCommandHandler.execute(updateImageCommand, null));
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(0)).save(any(Image.class));
    }

    /**
     * Test for file upload error during image update.
     * Ensures that a FileUploadException is thrown.
     */
    @Test
    void updateImage_fileUploadError_throwsFileUploadException() throws IOException {
        // Arrange
        Long imageId = 1L;
        Image existingImage = new Image();
        existingImage.setId(imageId);

        MultipartFile file = mock(MultipartFile.class);

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(existingImage));
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("testImage.jpg");
        when(file.getInputStream()).thenThrow(new IOException("File upload error"));

        UpdateImageCommand updateImageCommand = new UpdateImageCommand(imageId, file);

        // Act & Assert
        assertThrows(FileUploadException.class, () -> updateImageCommandHandler.execute(updateImageCommand, null));
        verify(imageRepository, times(1)).findById(imageId);
        verify(imageRepository, times(0)).save(any(Image.class));
    }
}

