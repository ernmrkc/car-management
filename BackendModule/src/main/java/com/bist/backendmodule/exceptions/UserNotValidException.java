package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not valid.
 */
public class UserNotValidException extends CustomBaseException {

    /**
     * Constructs a new UserNotValidException with the specified message and class.
     *
     * @param message The detail message about the exception
     * @param clazz   The class where the exception occurred
     */
    public UserNotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
