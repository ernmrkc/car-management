package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an image is not found.
 */
public class ImageNotFoundException extends CustomBaseException {

    /**
     * Constructs a new ImageNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public ImageNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Image not found."), clazz);
    }
}
