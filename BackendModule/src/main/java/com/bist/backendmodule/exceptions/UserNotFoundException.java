package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends CustomBaseException {

    /**
     * Constructs a new UserNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public UserNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("User not found."), clazz);
    }
}
