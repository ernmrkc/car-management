package com.bist.backendmodule.modules.image.query.handlers;

import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to handle the retrieval of an image by its ID.
 */
@Service
public class GetImageByIdQueryHandler implements Query<Long, Image> {
    private final ImageRepository imageRepository;

    public GetImageByIdQueryHandler(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Retrieves an image by its ID from the repository.
     *
     * @param id The ID of the image to retrieve
     * @return ResponseEntity containing the image
     * @throws ImageNotFoundException If the image with the specified ID is not found
     */
    @Override
    public ResponseEntity<Image> execute(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new ImageNotFoundException(GetImageByIdQueryHandler.class);
        }
        Image image = imageOptional.get();
        return ResponseEntity.ok().body(image);
    }
}
