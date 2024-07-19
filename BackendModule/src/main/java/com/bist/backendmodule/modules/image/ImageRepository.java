package com.bist.backendmodule.modules.image;

import com.bist.backendmodule.modules.image.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Image entity.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Finds an image by its full path.
     *
     * @param fullPath The full path of the image
     * @return An Optional containing the image if found, otherwise empty
     */
    Optional<Image> findByFullPath(String fullPath);
}
