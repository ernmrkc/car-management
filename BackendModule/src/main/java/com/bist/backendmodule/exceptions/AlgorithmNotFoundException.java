package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the specified algorithm is not found or implemented.
 */
public class AlgorithmNotFoundException extends CustomBaseException {

    /**
     * Constructs a new AlgorithmNotFoundException with the specified detail message and class.
     *
     * @param message The detail message about the exception
     * @param clazz   The class where the exception occurred
     */
    public AlgorithmNotFoundException(String message, Class<?> clazz) {
        super(HttpStatus.NOT_IMPLEMENTED, new SimpleResponse(message), clazz);
    }

    /**
     * Constructs a new AlgorithmNotFoundException with the specified detail message, cause, and class.
     *
     * @param message The detail message about the exception
     * @param cause   The cause of the exception
     * @param clazz   The class where the exception occurred
     */
    public AlgorithmNotFoundException(String message, Throwable cause, Class<?> clazz) {
        super(message, cause, HttpStatus.NOT_IMPLEMENTED, new SimpleResponse(message), clazz);
    }
}
