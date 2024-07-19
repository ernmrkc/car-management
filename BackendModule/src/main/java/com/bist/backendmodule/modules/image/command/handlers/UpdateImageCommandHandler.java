package com.bist.backendmodule.modules.image.command.handlers;

import com.bist.backendmodule.exceptions.FileEmptyException;
import com.bist.backendmodule.exceptions.FileUploadException;
import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.models.UpdateImageCommand;
import com.bist.backendmodule.validations.ImageValidationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

/**
 * Service for handling the updating of an image.
 */
@Service
public class UpdateImageCommandHandler implements Command<UpdateImageCommand, Void, Image> {

    @Setter                                         // Used for test class
    @Value("${upload.directory}")
    private String uploadDirectory;
    private final ImageRepository imageRepository;
    private final ImageValidationService imageValidationService;

    public UpdateImageCommandHandler(ImageRepository imageRepository,
                                     ImageValidationService imageValidationService) {
        this.imageRepository = imageRepository;
        this.imageValidationService = imageValidationService;
    }

    /**
     * Executes the update image command.
     *
     * @param updateImageCommand The command containing the image update information
     * @param bindingResult      Not used in this service
     * @return ResponseEntity containing the updated Image
     * @throws ImageNotFoundException If the image with the given ID is not found
     * @throws FileEmptyException     If the provided file is empty
     * @throws FileUploadException    If there is an error uploading the file
     */
    @Override
    public ResponseEntity<Image> execute(UpdateImageCommand updateImageCommand, Void bindingResult) {
        Optional<Image> imageOptional = imageRepository.findById(updateImageCommand.getImageId());
        if (imageOptional.isEmpty()) {
            throw new ImageNotFoundException(UpdateImageCommandHandler.class);
        }
        if (updateImageCommand.getFile().isEmpty()) {
            throw new FileEmptyException(UpdateImageCommandHandler.class);
        }
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(updateImageCommand.getFile().getOriginalFilename()));
            Path uploadPath = Paths.get(uploadDirectory);

            try (InputStream inputStream = updateImageCommand.getFile().getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            File uploadFile = new File(uploadDirectory + fileName);
            Image image = imageOptional.get();
            image.setFilename(fileName);
            image.setFullPath(uploadFile.getAbsolutePath());

            // Validate image
            BindingResult bindingResult1 = new BeanPropertyBindingResult(image, "image");
            imageValidationService.validateImage(image, bindingResult1, UpdateImageCommandHandler.class);

            // Save image
            imageRepository.save(image);
            return ResponseEntity.ok().body(image);
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage(), UpdateImageCommandHandler.class);
        }
    }
}
