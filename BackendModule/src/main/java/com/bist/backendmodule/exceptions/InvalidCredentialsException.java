package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when invalid credentials are provided.
 */
public class InvalidCredentialsException extends CustomBaseException {

    /**
     * Constructs a new InvalidCredentialsException with the specified cause and class.
     *
     * @param cause The cause of the exception
     * @param clazz The class where the exception occurred
     */
    public InvalidCredentialsException(Throwable cause, Class<?> clazz) {
        super("INVALID CREDENTIALS", cause, HttpStatus.BAD_REQUEST, new SimpleResponse("Invalid Credentials"), clazz);
    }
}
