package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to create a role that already exists.
 */
public class RoleAlreadyExistsException extends CustomBaseException {

    /**
     * Constructs a new RoleAlreadyExistsException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public RoleAlreadyExistsException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Role already exists."), clazz);
    }
}
