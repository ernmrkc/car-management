package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a specified role is not valid.
 */
public class RoleNotValidException extends CustomBaseException {

    /**
     * Constructs a new RoleNotValidException with the specified message and class.
     *
     * @param message The detail message explaining the reason for the exception
     * @param clazz   The class where the exception occurred
     */
    public RoleNotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
