package com.bist.backendmodule.validations;

import com.bist.backendmodule.exceptions.GroupNotValidException;
import com.bist.backendmodule.modules.group.models.Group;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

/**
 * Service class for validating Group entities.
 */
@Service
public class GroupValidationService {

    /**
     * Validates a Group entity and throws a GroupNotValidException if validation errors are found.
     *
     * @param group         the Group entity to validate
     * @param bindingResult the BindingResult containing validation results
     * @param clazz         the class where the validation is performed
     * @throws GroupNotValidException if validation errors are found
     */
    public void validateGroup(Group group, BindingResult bindingResult, Class<?> clazz) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            errorMessage += "Group Data: " + group;
            System.out.println(errorMessage);
            throw new GroupNotValidException(errorMessage, clazz);
        }
    }
}
