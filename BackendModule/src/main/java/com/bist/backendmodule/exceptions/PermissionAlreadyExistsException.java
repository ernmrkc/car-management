package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a login request is not valid.
 */
public class PermissionAlreadyExistsException extends CustomBaseException {

    /**
     * Constructs a new LoginRequestNotValidException with the specified detail message and class.
     *
     * @param message The detail message about why the login request is not valid
     * @param clazz   The class where the exception occurred
     */
    public PermissionAlreadyExistsException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Permission already exists."), clazz);
    }
}
