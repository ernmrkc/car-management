package com.bist.backendmodule.modules.role.models;

import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a Role.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ROLE")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions = new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    /**
     * Method to remove this role from users before deletion.
     * Ensures that the role is removed from the users' role lists
     * before the role itself is deleted.
     */
    @PreRemove
    private void removeRoleFromUser(){
        for (User user : users){
            user.getRoles().remove(this);
        }
    }

    /**
     * Returns the name of the role, which is used as the authority.
     *
     * @return the name of the role
     */
    @Override
    public String getAuthority() {
        return name;
    }
}
