package com.bist.backendmodule.modules.image.command.handlers;

import com.bist.backendmodule.exceptions.FileEmptyException;
import com.bist.backendmodule.exceptions.FileUploadException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.models.ImageDTO;
import com.bist.backendmodule.validations.ImageValidationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
 * Service class for handling the creation of images.
 */
@Service
public class CreateImageCommandHandler implements Command<MultipartFile, Void, ImageDTO> {

    @Setter                             // Used for test class
    @Value("${upload.directory}")
    private String uploadDirectory;

    private final ImageRepository imageRepository;
    private final ImageValidationService imageValidationService;

    public CreateImageCommandHandler(ImageRepository imageRepository,
                                     ImageValidationService imageValidationService) {
        this.imageRepository = imageRepository;
        this.imageValidationService = imageValidationService;
    }

    /**
     * Executes the command to create an image.
     *
     * @param file          The multipart file to be uploaded
     * @param bindingResult Not used in this service
     * @return Response Entity containing the created image DTO
     * @throws FileEmptyException  If the uploaded file is empty
     * @throws FileUploadException If there is an error during file upload
     */
    @Override
    public ResponseEntity<ImageDTO> execute(MultipartFile file, Void bindingResult) {
        if (file.isEmpty()) {
            throw new FileEmptyException(CreateImageCommandHandler.class);
        }
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path uploadPath = Paths.get(uploadDirectory);

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();

            File uploadFile = new File(uploadDirectory + fileName);

            Optional<Image> imageOptional = imageRepository.findByFullPath(uploadFile.getAbsolutePath());
            if (imageOptional.isPresent()) {
                return ResponseEntity.ok().body(new ImageDTO(imageOptional.get().getId(), fileDownloadUri));
            }

            Image image = new Image();
            image.setFilename(fileName);
            image.setFullPath(uploadFile.getAbsolutePath());

            BindingResult bindingResult1 = new BeanPropertyBindingResult(image, "image");
            imageValidationService.validateImage(image, bindingResult1, CreateImageCommandHandler.class);

            imageRepository.save(image);

            ImageDTO imageDTO = new ImageDTO(image.getId(), fileDownloadUri);

            return ResponseEntity.ok().body(imageDTO);
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage(), CreateImageCommandHandler.class);
        }
    }

}
