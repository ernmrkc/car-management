package com.bist.backendmodule.modules.car.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

/**
 * Request class for getting all cars with pagination and sorting.
 */
@Data
@AllArgsConstructor
public class GetAllCarsRequest {
    private Specification<Car> specification;
    private int pageSize;
    private int pageNo;
    private String sortBy;
    private String sortDir;
}
