package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.UserCreateDTONotValidException;
import com.bist.backendmodule.modules.user.models.UserCreateDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service class for validating UserCreateDTO objects.
 */
@Service
public class UserCreateDTOValidationService {

    /**
     * Validates a UserCreateDTO object and throws a UserCreateDTONotValidException if validation errors are found.
     *
     * @param userCreateDTO the UserCreateDTO object to validate
     * @param bindingResult the BindingResult containing validation results
     * @param clazz         the class where the validation is performed
     * @throws UserCreateDTONotValidException if validation errors are found
     */
    public void validateUserCreateDTO(UserCreateDTO userCreateDTO, BindingResult bindingResult, Class<?> clazz){
        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += "--> UserCreateDTO Data: " + userCreateDTO;
            System.out.println(errorMessage);
            throw new UserCreateDTONotValidException(errorMessage, clazz);
        }
    }
}
