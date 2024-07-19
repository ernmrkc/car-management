package com.bist.backendmodule.modules.car;

import com.bist.backendmodule.modules.car.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Car entities.
 * Extends JpaRepository for basic CRUD operations.
 * Extends JpaSpecificationExecutor for enabling query by specification.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
