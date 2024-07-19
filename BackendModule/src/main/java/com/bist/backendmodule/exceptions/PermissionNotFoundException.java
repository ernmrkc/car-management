package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested permission is not found.
 */
public class PermissionNotFoundException extends CustomBaseException {

    /**
     * Constructs a new PermissionNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public PermissionNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Permission not found"), clazz);
    }
}
