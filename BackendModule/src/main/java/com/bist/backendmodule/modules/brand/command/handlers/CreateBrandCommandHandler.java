package com.bist.backendmodule.modules.brand.command.handlers;

import com.bist.backendmodule.exceptions.BrandAlreadyExistsException;
import com.bist.backendmodule.exceptions.BrandNotValidException;
import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.brand.BrandRepository;
import com.bist.backendmodule.modules.brand.models.Brand;
import com.bist.backendmodule.validations.BrandValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/**
 * Service to handle the creation of a brand.
 */
@Service
public class CreateBrandCommandHandler implements Command<Brand, BindingResult, Void> {
    private final BrandRepository brandRepository;
    private final BrandValidationService brandValidationService;

    public CreateBrandCommandHandler(BrandRepository brandRepository,
                                     BrandValidationService brandValidationService) {
        this.brandRepository = brandRepository;
        this.brandValidationService = brandValidationService;
    }

    /**
     * Executes the command to create a brand.
     *
     * @param brand         The brand to be created
     * @param bindingResult Binding result for validation operations
     * @return Void
     * @throws BrandAlreadyExistsException If the brand with the specified name already exists.
     */
    @Override
    public ResponseEntity<Void> execute(Brand brand, BindingResult bindingResult) throws BrandNotValidException {
        // Validate brand
        brandValidationService.validateBrand(brand, bindingResult, CreateBrandCommandHandler.class);

        // Check if the brand is already exists
        Optional<Brand> brandOptional = brandRepository.findByName(brand.getName());
        if (brandOptional.isPresent()) {
            throw new BrandAlreadyExistsException(CreateBrandCommandHandler.class);
        }
        brandRepository.save(brand);
        return ResponseEntity.ok().build();
    }
}
