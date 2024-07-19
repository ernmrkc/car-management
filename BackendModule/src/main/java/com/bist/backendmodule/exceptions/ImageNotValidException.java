package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an image is not valid.
 */
public class ImageNotValidException extends CustomBaseException {

    /**
     * Constructs a new ImageNotValidException with the specified message and class.
     *
     * @param message The detail message
     * @param clazz   The class where the exception occurred
     */
    public ImageNotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
