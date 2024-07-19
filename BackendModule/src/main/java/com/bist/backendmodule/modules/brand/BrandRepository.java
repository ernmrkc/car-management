package com.bist.backendmodule.modules.brand;

import com.bist.backendmodule.modules.brand.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Brand entities.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * Finds a brand by name.
     *
     * @param name The name of the brand
     * @return An optional containing the found brand, or an empty Optional if no brand found
     */
    Optional<Brand> findByName(String name);
}
