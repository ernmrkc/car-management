package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.LoginRequestNotValidException;
import com.bist.backendmodule.security.models.LoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service class for validating LoginRequest objects.
 */
@Service
public class LoginRequestValidationService {

    /**
     * Validates a LoginRequest object and throws a LoginRequestNotValidException if validation errors are found.
     *
     * @param loginRequest  the LoginRequest object to validate
     * @param bindingResult the BindingResult containing validation results
     * @param clazz         the class where the validation is performed
     * @throws LoginRequestNotValidException if validation errors are found
     */
    public void validateLoginRequest(LoginRequest loginRequest, BindingResult bindingResult, Class<?> clazz) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += "LoginRequest Data: " + loginRequest;
            throw new LoginRequestNotValidException(errorMessage, clazz);
        }
    }
}
