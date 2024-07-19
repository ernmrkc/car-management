package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.BrandNotValidException;
import com.bist.backendmodule.modules.brand.models.Brand;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service for validating Brand entities.
 */
@Service
public class BrandValidationService {

    /**
     * Validates a Brand entity.
     *
     * @param brand         the Brand entity to validate
     * @param bindingResult the BindingResult containing validation errors
     * @param clazz         the class where the validation occurred
     * @throws BrandNotValidException If validation errors are found
     */
    public void validateBrand(Brand brand, BindingResult bindingResult, Class<?> clazz) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += " --> Brand Data: " + brand;
            System.out.println(errorMessage);
            throw new BrandNotValidException(errorMessage, clazz);
        }
    }
}
