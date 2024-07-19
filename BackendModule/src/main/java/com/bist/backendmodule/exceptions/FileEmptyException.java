package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an uploaded file is empty.
 */
public class FileEmptyException extends CustomBaseException {

    /**
     * Constructs a new FileEmptyException with the specified class.
     *
     * @param clazz The class where the exception occurred
     */
    public FileEmptyException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("Failed to load file: File is empty."), clazz);
    }
}
