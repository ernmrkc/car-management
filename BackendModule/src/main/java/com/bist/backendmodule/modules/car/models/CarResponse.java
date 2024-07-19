package com.bist.backendmodule.modules.car.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Response class for paginated car data.
 */
@Data
@AllArgsConstructor
public class CarResponse {
    private List<Car> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    /**
     * Constructs a CarResponse from a Page of Car entities.
     *
     * @param carPage The Page containing car data
     */
    public CarResponse(Page<Car> carPage) {
        this.content = carPage.getContent();
        this.pageNo = carPage.getNumber();
        this.pageSize = carPage.getSize();
        this.totalElements = carPage.getTotalElements();
        this.totalPages = carPage.getTotalPages();
        this.last = carPage.isLast();
    }
}
