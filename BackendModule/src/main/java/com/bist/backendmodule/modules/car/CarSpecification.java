package com.bist.backendmodule.modules.car;

import com.bist.backendmodule.modules.car.models.Car;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Class containing specifications for querying Car entities.
 */
public class CarSpecification {

    /**
     * Specification for filtering cars by brand name.
     *
     * @param brandName The brand name to filter by
     * @return The specification for filtering cars by brand name
     */
    public static Specification<Car> hasBrand(String brandName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("name"), brandName);
    }

    /**
     * Specification for filtering cars by specification.
     *
     * @param specification The specification to filter by
     * @return The specification for filtering cars by specification
     */
    public static Specification<Car> hasSpecification(String specification) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("specification"), "%" + specification + "%");
    }

    /**
     * Specification for filtering cars by engine liter.
     *
     * @param engineLiter The engine liter to filter by
     * @return The specification for filtering cars by engine liter
     */
    public static Specification<Car> hasEngineLiter(Float engineLiter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("engineLiter"), engineLiter);
    }

    /**
     * Specification for filtering cars by new status.
     *
     * @param isNew The new status to filter by
     * @return The specification for filtering cars by new status
     */
    public static Specification<Car> isNew(Boolean isNew) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isNew"), isNew);
    }

    /**
     * Specification for filtering cars by price greater than or equal to a given value.
     *
     * @param price The minimum price to filter by
     * @return The specification for filtering cars by price greater than or equal to the given value
     */
    public static Specification<Car> hasPriceGreaterThanOrEqual(BigDecimal price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    /**
     * Specification for filtering cars by price less than or equal to a given value.
     *
     * @param price The maximum price to filter by
     * @return The specification for filtering cars by price less than or equal to the given value
     */
    public static Specification<Car> hasPriceLessThanOrEqual(BigDecimal price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    /**
     * Specification for filtering cars by release date after a given date.
     *
     * @param dateTime The minimum release date to filter by
     * @return The specification for filtering cars by release date after the given date
     */
    public static Specification<Car> hasReleaseDateTimeAfter(LocalDateTime dateTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDateTime"), dateTime);
    }

    /**
     * Specification for filtering cars by release date before a given date.
     *
     * @param dateTime The maximum release date to filter by
     * @return The specification for filtering cars by release date before the given date
     */
    public static Specification<Car> hasReleaseDateTimeBefore(LocalDateTime dateTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("releaseDateTime"), dateTime);
    }

    /**
     * Specification for filtering cars by a search term.
     *
     * @param searchTerm The search term to filter by
     * @return The specification for filtering cars by the search term
     */
    public static Specification<Car> hasSearchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("specification"), "%" + searchTerm + "%"),
                        criteriaBuilder.like(root.get("brand").get("name"), "%" + searchTerm + "%")
                );
    }
}
