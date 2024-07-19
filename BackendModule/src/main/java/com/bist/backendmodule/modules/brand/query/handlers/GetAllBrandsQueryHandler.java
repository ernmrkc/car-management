package com.bist.backendmodule.modules.brand.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling the retrieval of all brands.
 */
@Service
public class GetAllBrandsQueryHandler implements Query<Void, List<Brand>> {
    private final BrandRepository brandRepository;

    public GetAllBrandsQueryHandler(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Executes the query to retrieve all brands from the repository.
     *
     * @param input Not used in this service
     * @return ResponseEntity containing list of all brands
     */
    @Override
    public ResponseEntity<List<Brand>> execute(Void input) {
        List<Brand> brandList = brandRepository.findAll();
        return ResponseEntity.ok().body(brandList);
    }
}
