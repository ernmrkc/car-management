package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a brand is not valid.
 */
public class BrandNotValidException extends CustomBaseException {

    /**
     * Constructs a new BrandNotValidException with the specified message and class.
     *
     * @param message The detail message explaining why the exception occurred
     * @param clazz   The class where the exception occurred
     */
    public BrandNotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
