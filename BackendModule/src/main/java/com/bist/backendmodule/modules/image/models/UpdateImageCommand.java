package com.bist.backendmodule.modules.image.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Command class for updating an image.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImageCommand {
    private Long imageId;
    private MultipartFile file;
}
