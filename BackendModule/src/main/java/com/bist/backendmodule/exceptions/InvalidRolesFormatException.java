package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the format of the roles is invalid.
 */
public class InvalidRolesFormatException extends CustomBaseException {

    /**
     * Constructs a new InvalidRolesFormatException with the specified detail message and class.
     *
     * @param message The detail message about why the roles format is invalid
     * @param clazz   The class where the exception occurred
     */
    public InvalidRolesFormatException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
