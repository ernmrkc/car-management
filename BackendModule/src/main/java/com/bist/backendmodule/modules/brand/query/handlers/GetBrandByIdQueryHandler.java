package com.bist.backendmodule.modules.brand.query.handlers;

import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling the retrieval of a brand by its ID.
 */
@Service
public class GetBrandByIdQueryHandler implements Query<Long, Brand> {
    private final BrandRepository brandRepository;

    public GetBrandByIdQueryHandler(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Executes the query to retrieve a brand by its ID.
     *
     * @param id The ID of the brand to be retrieved.
     * @return ResponseEntity containing the retrieved brand.
     * @throws BrandNotFoundException If the brand with the specified ID is not found.
     */
    @Override
    public ResponseEntity<Brand> execute(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            throw new BrandNotFoundException(GetBrandByIdQueryHandler.class);
        }
        Brand brand = brandOptional.get();
        return ResponseEntity.ok().body(brand);
    }
}
