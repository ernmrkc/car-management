package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.CarCommandNotValidException;
import com.bist.backendmodule.modules.car.models.CarCommand;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service for validating CarCommand entities.
 */
@Service
public class CarCommandValidationService {

    /**
     * Validates a CarCommand entity.
     *
     * @param carCommand    The CarCommand entity to validate
     * @param bindingResult The BindingResult containing validation errors
     * @param clazz         The class where the validation occurred
     * @throws CarCommandNotValidException If validation errors are found
     */
    public void validateCarCommand(CarCommand carCommand, BindingResult bindingResult, Class<?> clazz) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += " --> CarCreateDTO Data: " + carCommand;
            System.out.println(errorMessage);
            throw new CarCommandNotValidException(errorMessage, clazz);
        }
    }
}
