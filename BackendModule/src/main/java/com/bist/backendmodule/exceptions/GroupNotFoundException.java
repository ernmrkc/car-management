package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when attempting to access a group that does not exist.
 */
public class GroupNotFoundException extends CustomBaseException {

    /**
     * Constructs a new GroupNotFoundException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public GroupNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Group not found."), clazz);
    }
}
