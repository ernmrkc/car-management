package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the token signature is invalid.
 */
public class InvalidTokenSignatureException extends CustomBaseException {

    /**
     * Constructs a new InvalidTokenSignatureException with the specified detail message and class.
     *
     * @param message The detail message about why the token signature is invalid
     * @param clazz   The class where the exception occurred
     */
    public InvalidTokenSignatureException(String message, Class<?> clazz) {
        super(HttpStatus.UNAUTHORIZED, new SimpleResponse(message), clazz);
    }

    /**
     * Constructs a new InvalidTokenSignatureException with the specified detail message, cause, and class.
     *
     * @param message The detail message about why the token signature is invalid
     * @param cause   The cause of the exception
     * @param clazz   The class where the exception occurred
     */
    public InvalidTokenSignatureException(String message, Throwable cause, Class<?> clazz) {
        super(message, cause, HttpStatus.UNAUTHORIZED, new SimpleResponse(message), clazz);
    }
}
