package com.bist.backendmodule.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * A custom base exception class that extends {@link RuntimeException}.
 * This class is used to create custom exceptions with an HTTP status,
 * a simple response message, and the originating class.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomBaseException extends RuntimeException {
    private HttpStatus status;
    private SimpleResponse simpleResponse;
    private Class<?> clazz;                     // The class where the exception thrown

    /**
     * Constructs a new CustomBaseException with the specified details.
     *
     * @param message        The detail message about the exception
     * @param cause          The cause of the exception
     * @param status         The HTTP status associated with the exception
     * @param simpleResponse The simple response message
     * @param clazz          The class where the exception is thrown
     */
    public CustomBaseException(String message, Throwable cause, HttpStatus status, SimpleResponse simpleResponse, Class<?> clazz) {
        super(message, cause);
        this.status = status;
        this.simpleResponse = simpleResponse;
        this.clazz = clazz;
    }
}
