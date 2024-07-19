package com.bist.backendmodule.modules.brand.command.handlers;

import com.bist.backendmodule.exceptions.BrandAlreadyExistsException;
import com.bist.backendmodule.exceptions.BrandNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.modules.brand.models.UpdateBrandCommand;
import com.bist.backendmodule.validations.BrandValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/**
 * Service to handle the updating of a brand.
 */
@Service
public class UpdateBrandCommandHandler implements Command<UpdateBrandCommand, BindingResult, Brand> {
    private final BrandRepository brandRepository;
    private final BrandValidationService brandValidationService;

    public UpdateBrandCommandHandler(BrandRepository brandRepository,
                                     BrandValidationService brandValidationService) {
        this.brandRepository = brandRepository;
        this.brandValidationService = brandValidationService;
    }

    /**
     * Executes the command to update a brand.
     *
     * @param updateBrandCommand The command object containing the brand details to be updated
     * @param bindingResult      The binding result for validation
     * @return Updated brand
     * @throws BrandNotFoundException      If the brand with the specified ID is not found.
     * @throws BrandAlreadyExistsException If the brand with the specified name already exists.
     */
    @Override
    public ResponseEntity<Brand> execute(UpdateBrandCommand updateBrandCommand, BindingResult bindingResult) {
        Optional<Brand> brandOptional = brandRepository.findById(updateBrandCommand.getId());

        // Check if brand is available
        if (brandOptional.isEmpty()) {
            throw new BrandNotFoundException(UpdateBrandCommandHandler.class);
        }

        Brand brand = updateBrandCommand.getBrand();

        // Check brand
        brandValidationService.validateBrand(brand, bindingResult, UpdateBrandCommandHandler.class);

        // Check if brand is already exists
        brandOptional = brandRepository.findByName(brand.getName());
        if (brandOptional.isPresent()) {
            throw new BrandAlreadyExistsException(UpdateBrandCommandHandler.class);
        }

        brand.setId(updateBrandCommand.getId());
        brandRepository.save(brand);
        return ResponseEntity.ok().body(brand);
    }
}
