package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a car is not found.
 */
public class CarNotFoundException extends CustomBaseException {

    /**
     * Constructs a new CarNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public CarNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Car not found"), clazz);
    }
}
