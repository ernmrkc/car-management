package com.bist.backendmodule.modules.user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for creating a new user.
 * This class contains all the necessary information required to create a new user.
 */
@Data
public class UserCreateDTO {

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 20, message = "Username must not exceed 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(max = 16, message = "Phone number must not exceed 16 characters")
    private String phoneNumber;

    private List<Long> roleIds;
    private List<Long> groupIds;
}
