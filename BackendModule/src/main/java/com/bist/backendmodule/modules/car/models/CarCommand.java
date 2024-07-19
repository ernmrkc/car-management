package com.bist.backendmodule.modules.car.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Base command class for creating or updating a car.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarCommand {

    @NotNull(message = "Brand cannot be empty")
    private Long brandId;

    @NotEmpty(message = "Specification cannot be empty")
    private String specification;

    @NotNull(message = "Motor litre cannot be empty")
    private Float engineLiter;

    @NotNull(message = "Car status field cannot be empty")
    private Boolean isNew;

    @NotNull(message = "Price cannot be empty")
    private BigDecimal price;

    @NotNull(message = "Release date time cannot be empty")
    private LocalDateTime releaseDateTime;

    private Long imageId;
}
