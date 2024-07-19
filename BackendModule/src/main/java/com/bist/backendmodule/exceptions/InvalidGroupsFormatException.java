package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the format of the groups is invalid.
 */
public class InvalidGroupsFormatException extends CustomBaseException {

    /**
     * Constructs a new InvalidGroupsFormatException with the specified detail message and class.
     *
     * @param message The detail message about why the groups format is invalid
     * @param clazz   The class where the exception occurred
     */
    public InvalidGroupsFormatException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
