package com.bist.backendmodule.modules.permission.models;

import com.bist.backendmodule.modules.role.models.Role;
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
 * Entity representing a permission.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_PERMISSION")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    /**
     * Method to remove this permission from roles before deletion.
     * Ensures that the permission is removed from the roles' permission lists
     * before the permission itself is deleted.
     */
    @PreRemove
    private void removePermissionFromRole(){
        for (Role role : roles){
            role.getPermissions().remove(this);
        }
    }

    /**
     * Returns the name of the permission, which is used as the authority.
     *
     * @return the name of the permission
     */
    @Override
    public String getAuthority() {
        return name;
    }
}
