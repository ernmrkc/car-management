package image.controller.tests;

import com.bist.backendmodule.BackendModuleApplication;
import com.bist.backendmodule.exceptions.FileEmptyException;
import com.bist.backendmodule.exceptions.FileUploadException;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.command.handlers.CreateImageCommandHandler;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.models.ImageDTO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BackendModuleApplication.class)
public class CreateImageCommandHandlerTest {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageValidationService imageValidationService;
    @InjectMocks
    private CreateImageCommandHandler createImageCommandHandler;
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
        createImageCommandHandler = new CreateImageCommandHandler(imageRepository, imageValidationService);
        createImageCommandHandler.setUploadDirectory(uploadDirectory);

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
     * Test for successfully creating an image.
     * Ensures the file is uploaded, saved, and the correct response is returned.
     */
    @Test
    public void createImage_validFile_returnSuccess(){
        // Arrange
        String fileName = mockMultipartFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, fileName);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();

        when(imageRepository.findByFullPath(filePath.toAbsolutePath().toString()))
                .thenReturn(Optional.empty());

        Image image = new Image();
        image.setFilename(fileName);
        image.setFullPath(filePath.toAbsolutePath().toString());

        when(imageRepository.save(any(Image.class))).thenReturn(image);

        // Act
        ResponseEntity<ImageDTO> response = createImageCommandHandler.execute(mockMultipartFile, null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(fileDownloadUri, response.getBody().getDownloadUri());
        assertEquals(image.getId(), response.getBody().getImageId());

        verify(imageRepository, times(1)).findByFullPath(filePath.toAbsolutePath().toString());
        verify(imageRepository, times(1)).save(any(Image.class));
    }

    /**
     * Test for handling an empty file upload.
     * Ensures that a FileEmptyException is thrown.
     */
    @Test
    void createImage_emptyFile_throwsFileEmptyException() {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        // Act & Assert
        assertThrows(FileEmptyException.class, () -> createImageCommandHandler.execute(file, null));
    }

    /**
     * Test for handling a file upload error.
     * Ensures that a FileUploadException is thrown.
     */
    @Test
    void createImage_fileUploadError_throwsFileUploadException() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("testImage.jpg");
        when(file.getInputStream()).thenThrow(new IOException("File upload error"));

        // Act & Assert
        assertThrows(FileUploadException.class, () -> createImageCommandHandler.execute(file, null));
    }
}
