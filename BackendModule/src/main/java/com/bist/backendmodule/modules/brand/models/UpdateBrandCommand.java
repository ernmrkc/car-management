package com.bist.backendmodule.modules.brand.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Command class for updating a brand.
 */
@Data
@AllArgsConstructor
public class UpdateBrandCommand {
    private Long id;
    private Brand brand;
}
