package com.bist.backendmodule.modules.image.models;

import com.bist.backendmodule.modules.car.models.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * Entity representing an image.
 */
@Entity
@Data
@Table(name = "T_IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Filename cannot be empty")
    @Column(name = "filename")
    private String filename;

    @NotEmpty(message = "File path cannot be empty")
    @Column(name = "file_path")
    private String fullPath;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Car> cars;
}
