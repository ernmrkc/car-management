package com.bist.backendmodule.modules.user.models;

import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.modules.role.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a user in the system.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 20, message = "Username must not exceed 20 characters")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 32, max = 60, message = "Password must be between 32 and 60 characters")        // BCrypt hash length is 60, MD5 hash length is 32
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(max = 16, message = "Phone number must not exceed 16 characters")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotNull(message = "Password expiration date cannot be empty")
    @Column(name = "pwd_expiration_date", nullable = false)
    private LocalDateTime passwordExpirationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups = new ArrayList<>();

    /**
     * Constructs a new User entity from a {@link UserCreateDTO}.
     *
     * @param userCreateDTO The DTO containing user creation data
     */
    public User(UserCreateDTO userCreateDTO){
        this.username = userCreateDTO.getUsername();
        this.email = userCreateDTO.getEmail();
        this.name = userCreateDTO.getName();
        this.surname = userCreateDTO.getSurname();
        this.phoneNumber = userCreateDTO.getPhoneNumber();
    }
}
