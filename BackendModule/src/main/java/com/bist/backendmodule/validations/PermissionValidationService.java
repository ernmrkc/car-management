package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.PermissionNotValidException;
import com.bist.backendmodule.modules.permission.models.Permission;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service class for validating Permission objects.
 */
@Service
public class PermissionValidationService {

    /**
     * Validates a Permission object and throws a PermissionNotValidException if validation errors are found.
     *
     * @param permission    the Permission object to validate
     * @param bindingResult the BindingResult containing validation results
     * @param clazz         the class where the validation is performed
     * @throws PermissionNotValidException if validation errors are found
     */
    public void validatePermission(Permission permission, BindingResult bindingResult, Class<?> clazz){
        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += "Permission Data: " + permission;
            System.out.println(errorMessage);
            throw new PermissionNotValidException(errorMessage, clazz);
        }
    }
}
