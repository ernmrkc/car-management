package com.bist.backendmodule.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is used to encapsulate a message during exception handling.
 */
@Data
@AllArgsConstructor
public class SimpleResponse {
    private String message;
}
