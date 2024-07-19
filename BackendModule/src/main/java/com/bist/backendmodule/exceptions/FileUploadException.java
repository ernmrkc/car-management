package com.bist.backendmodule.exceptions;

import com.bist.backendmodule.exceptions.models.CustomBaseException;
import com.bist.backendmodule.exceptions.models.SimpleResponse;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a file upload fails.
 */
public class FileUploadException extends CustomBaseException {

    /**
     * Constructs a new FileUploadException with the specified message and class.
     *
     * @param message The detailed message for the exception
     * @param clazz   The class where the exception occurred
     */
    public FileUploadException(String message, Class<?> clazz) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new SimpleResponse("Failed to load file: " + message), clazz);
    }
}
