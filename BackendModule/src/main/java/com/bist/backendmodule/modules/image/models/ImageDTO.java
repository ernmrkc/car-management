package com.bist.backendmodule.modules.image.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for Image.
 */
@Data
@AllArgsConstructor
public class ImageDTO {
    private Long imageId;
    private String downloadUri;
}
