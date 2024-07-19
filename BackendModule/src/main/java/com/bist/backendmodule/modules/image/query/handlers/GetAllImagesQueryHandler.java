package com.bist.backendmodule.modules.image.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.image.ImageRepository;
import com.bist.backendmodule.modules.image.models.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle the retrieval of all images.
 */
@Service
public class GetAllImagesQueryHandler implements Query<Void, List<Image>> {
    private final ImageRepository imageRepository;

    public GetAllImagesQueryHandler(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Retrieves all images from the repository.
     *
     * @param input Not used in this implementation
     * @return ResponseEntity containing the list of all images
     */
    @Override
    public ResponseEntity<List<Image>> execute(Void input) {
        List<Image> imageList = imageRepository.findAll();
        return ResponseEntity.ok().body(imageList);
    }
}
