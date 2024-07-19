package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.ImageNotValidException;
import com.bist.backendmodule.modules.image.models.Image;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

/**
 * Service for validating Image entities.
 */
@Service
public class ImageValidationService {
    private final Validator validator;

    /**
     * Constructs an ImageValidationService with the given Validator.
     *
     * @param validator The Validator to use for validation
     */
    public ImageValidationService(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates an Image entity.
     *
     * @param image         The Image entity to validate
     * @param bindingResult The BindingResult containing validation errors
     * @param clazz         The class where the validation occurred
     * @throws ImageNotValidException If validation errors are found
     */
    public void validateImage(Image image, BindingResult bindingResult, Class<?> clazz) {
        validator.validate(image, bindingResult);

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += " --> Image Data: " + image;
            System.out.println(errorMessage);
            throw new ImageNotValidException(errorMessage, clazz);
        }
    }
}
