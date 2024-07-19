package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.UserNotValidException;
import com.bist.backendmodule.modules.user.models.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

/**
 * Service class for validating User objects.
 */
@Service
public class UserValidationService {
    private final Validator validator;

    /**
     * Constructs a new UserValidationService with the specified validator.
     *
     * @param validator the Validator to use for validation
     */
    public UserValidationService(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates a User object and throws a UserNotValidException if validation errors are found.
     *
     * @param user          the User object to validate
     * @param bindingResult the BindingResult containing validation results
     * @param clazz         the class where the validation is performed
     * @throws UserNotValidException if validation errors are found
     */
    public void validateUser(User user, BindingResult bindingResult, Class<?> clazz) {
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(","));
            errorMessage += "--> User Data: " + user;
            System.out.println(errorMessage);
            throw new UserNotValidException(errorMessage, clazz);
        }
    }
}
