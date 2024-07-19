package com.bist.backendmodule.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * A data transfer object (DTO) that represents the response containing the token for a user.
 */
@Data
@AllArgsConstructor
public class TokenResponse implements Serializable {
    private String username;
    private String token;
}
