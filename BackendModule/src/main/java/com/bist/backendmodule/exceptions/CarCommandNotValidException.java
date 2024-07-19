package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a car command is not valid.
 */
public class CarCommandNotValidException extends CustomBaseException {

    /**
     * Constructs a new CarCommandNotValidException with the specified message and class.
     *
     * @param message The detail message explaining why the exception occurred
     * @param clazz   The class where the exception occurred
     */
    public CarCommandNotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
