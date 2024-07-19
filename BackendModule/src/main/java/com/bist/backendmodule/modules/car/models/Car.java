package com.bist.backendmodule.modules.car.models;

import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.image.models.Image;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a car.
 */
@Entity
@Data
@Table(name = "T_CAR")
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "specification")
    private String specification;

    @Column(name = "engine_liter")
    private Float engineLiter;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "release_date_time")
    private LocalDateTime releaseDateTime;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Car(CarCommand carCommand) {
        this.specification = carCommand.getSpecification();
        this.engineLiter = carCommand.getEngineLiter();
        this.isNew = carCommand.getIsNew();
        this.price = carCommand.getPrice();
        this.releaseDateTime = carCommand.getReleaseDateTime();
    }
}
