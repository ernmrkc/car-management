package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.RoleNotValidException;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service class for validating Role objects.
 */
@Service
public class RoleValidationService {

    /**
     * Validates a Role object and throws a RoleNotValidException if validation errors are found.
     *
     * @param role          the Role object to validate
     * @param bindingResult the BindingResult containing validation results
     * @param clazz         the class where the validation is performed
     * @throws RoleNotValidException if validation errors are found
     */
    public void validateRole(Role role, BindingResult bindingResult, Class<?> clazz) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += "Role Data: " + role;
            System.out.println(errorMessage);
            throw new RoleNotValidException(errorMessage, clazz);
        }
    }
}
