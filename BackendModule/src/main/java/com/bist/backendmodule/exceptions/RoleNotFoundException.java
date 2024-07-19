package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a specified role is not found.
 */
public class RoleNotFoundException extends CustomBaseException {

    /**
     * Constructs a new RoleNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public RoleNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Role not found"), clazz);
    }
}
