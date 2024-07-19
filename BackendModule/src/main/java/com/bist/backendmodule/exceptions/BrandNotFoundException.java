package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a specified brand is not found.
 */
public class BrandNotFoundException extends CustomBaseException {

    /**
     * Constructs a new BrandNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public BrandNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Brand not found."), clazz);
    }
}
