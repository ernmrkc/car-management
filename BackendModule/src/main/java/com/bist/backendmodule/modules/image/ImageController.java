package com.bist.backendmodule.modules.image;

import com.bist.backendmodule.modules.image.command.handlers.CreateImageCommandHandler;
import com.bist.backendmodule.modules.image.command.handlers.DeleteImageCommandHandler;
import com.bist.backendmodule.modules.image.command.handlers.UpdateImageCommandHandler;
import com.bist.backendmodule.modules.image.models.Image;
import com.bist.backendmodule.modules.image.models.ImageDTO;
import com.bist.backendmodule.modules.image.models.UpdateImageCommand;
import com.bist.backendmodule.modules.image.query.handlers.GetAllImagesQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for handling image-related operations.
 */
@RestController
@RequestMapping("/image")
@Validated
@Tag(name = "Image Controller", description = "Operations related to images")
public class ImageController {
    private final CreateImageCommandHandler createImageCommandHandler;
    private final GetAllImagesQueryHandler getAllImagesQueryHandler;
    private final UpdateImageCommandHandler updateImageCommandHandler;
    private final DeleteImageCommandHandler deleteImageCommandHandler;

    public ImageController(CreateImageCommandHandler createImageCommandHandler,
                           GetAllImagesQueryHandler getAllImagesQueryHandler,
                           UpdateImageCommandHandler updateImageCommandHandler,
                           DeleteImageCommandHandler deleteImageCommandHandler) {
        this.createImageCommandHandler = createImageCommandHandler;
        this.getAllImagesQueryHandler = getAllImagesQueryHandler;
        this.updateImageCommandHandler = updateImageCommandHandler;
        this.deleteImageCommandHandler = deleteImageCommandHandler;
    }

    /**
     * Endpoint for uploading a file.
     *
     * @param file The file to upload
     * @return ResponseEntity containing the ImageDTO
     */
    @PostMapping
    @Operation(summary = "Upload a file", description = "Uploads a file and returns the uploaded ImageDTO.")
    public ResponseEntity<ImageDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        return createImageCommandHandler.execute(file, null);
    }

    /**
     * Endpoint for retrieving all images.
     *
     * @return ResponseEntity containing a list of images
     */
    @GetMapping
    @Operation(summary = "Get all images", description = "Retrieves a list of all images.")
    ResponseEntity<List<Image>> getAllImages() {
        return getAllImagesQueryHandler.execute(null);
    }

    /**
     * Endpoint for updating an image.
     *
     * @param id   The ID of the image to update
     * @param file The new file to update
     * @return ResponseEntity containing the updated image
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an image", description = "Updates an image with the given ID and new file.")
    ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        UpdateImageCommand updateImageCommand = new UpdateImageCommand(id, file);
        return updateImageCommandHandler.execute(updateImageCommand, null);
    }

    /**
     * Endpoint for deleting an image.
     *
     * @param id The ID of the image to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an image", description = "Deletes the image with the given ID.")
    ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        return deleteImageCommandHandler.execute(id, null);
    }
}
