package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to create a group that already exists.
 */
public class GroupAlreadyExistsException extends CustomBaseException {

    /**
     * Constructs a new GroupAlreadyExistsException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public GroupAlreadyExistsException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Group already exists"), clazz);
    }
}
