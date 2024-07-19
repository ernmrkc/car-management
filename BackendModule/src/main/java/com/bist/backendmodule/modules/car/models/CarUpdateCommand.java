package com.bist.backendmodule.modules.car.models;

import com.bist.backendmodule.modules.image.models.UpdateImageCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command class for updating a car.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarUpdateCommand {
    private Long carId;
    private CarCommand carCommand;
    private UpdateImageCommand updateImageCommand;
}
