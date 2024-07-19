package com.bist.backendmodule.modules.image.command.handlers;

import com.bist.backendmodule.exceptions.ImageNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling the deletion of an image.
 */
@Service
public class DeleteImageCommandHandler implements Command<Long, Void, Void> {
    private final ImageRepository imageRepository;

    public DeleteImageCommandHandler(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Executes the delete image command.
     *
     * @param id            The ID of the image to be deleted
     * @param bindingResult Not used in this service
     * @return ResponseEntity indicating the result of the operation
     * @throws ImageNotFoundException If the image with the given ID is not found
     */
    @Override
    public ResponseEntity<Void> execute(Long id, Void bindingResult) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new ImageNotFoundException(DeleteImageCommandHandler.class);
        }
        Image image = imageOptional.get();
        imageRepository.delete(image);
        return ResponseEntity.ok().build();
    }
}
