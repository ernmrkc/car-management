package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the UserCreateDTO object is not valid.
 */
public class UserCreateDTONotValidException extends CustomBaseException {

    /**
     * Constructs a new UserCreateDTONotValidException with the specified message and class.
     *
     * @param message The detail message
     * @param clazz   The class where the exception occurred
     */
    public UserCreateDTONotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
