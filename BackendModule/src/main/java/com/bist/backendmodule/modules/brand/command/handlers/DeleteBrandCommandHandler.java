package com.bist.backendmodule.modules.brand.command.handlers;

import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to handle the deletion of a brand.
 */
@Service
public class DeleteBrandCommandHandler implements Command<Long, Void, Void> {
    private final BrandRepository brandRepository;

    public DeleteBrandCommandHandler(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Executes the command to delete a brand.
     *
     * @param id            The ID of the brand to be deleted
     * @param bindingResult Not used in this service
     * @return Void
     * @throws BrandNotFoundException If the brand with the specified ID is not found.
     */
    @Override
    public ResponseEntity<Void> execute(Long id, Void bindingResult) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            throw new BrandNotFoundException(DeleteBrandCommandHandler.class);
        }
        Brand brand = brandOptional.get();
        brandRepository.delete(brand);
        return ResponseEntity.ok().build();
    }
}
