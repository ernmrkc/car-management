package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a permission is not valid.
 */
public class PermissionNotValidException extends CustomBaseException {

    /**
     * Constructs a new PermissionNotValidException with the specified detail message and class.
     *
     * @param message The detail message about why the permission is not valid
     * @param clazz   The class where the exception occurred
     */
    public PermissionNotValidException(String message, Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message), clazz);
    }
}
