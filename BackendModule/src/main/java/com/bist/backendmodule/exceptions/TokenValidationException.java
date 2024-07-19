package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when token validation fails.
 */
public class TokenValidationException extends CustomBaseException {

    /**
     * Constructs a new TokenValidationException with the specified message and class.
     *
     * @param message The detail message explaining the reason for the exception
     * @param clazz   The class where the exception occurred
     */
    public TokenValidationException(String message, Class<?> clazz) {
        super(HttpStatus.UNAUTHORIZED, new SimpleResponse(message), clazz);
    }

    /**
     * Constructs a new TokenValidationException with the specified message, cause, and class.
     *
     * @param message The detail message explaining the reason for the exception
     * @param cause   The cause of the exception
     * @param clazz   The class where the exception occurred
     */
    public TokenValidationException(String message, Throwable cause, Class<?> clazz) {
        super(message, cause, HttpStatus.UNAUTHORIZED, new SimpleResponse(message), clazz);
    }
}
