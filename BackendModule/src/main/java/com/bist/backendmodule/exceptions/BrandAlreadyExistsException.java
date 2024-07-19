package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to create a brand that already exists.
 */
public class BrandAlreadyExistsException extends CustomBaseException {

    /**
     * Constructs a new BrandAlreadyExistsException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public BrandAlreadyExistsException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Brand already exists."), clazz);
    }
}
