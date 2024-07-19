package com.bist.backendmodule.security.models;

import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.role.models.Role;
import com.bist.backendmodule.modules.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a token request containing user details and authorities.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private LocalDateTime passwordExpirationDate;
    private List<Role> roles;
    private List<Permission> permissions;
    private List<Group> groups;

    /**
     * Constructs a TokenRequest from a User object.
     *
     * @param user The User object to create the TokenRequest from.
     */
    public TokenRequest(User user){
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.passwordExpirationDate = user.getPasswordExpirationDate();
        this.roles = user.getRoles();
        this.permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .distinct()
                .collect(Collectors.toList());
        this.groups = user.getGroups();
    }

    /**
     * Returns the authorities granted to the user. This includes roles and permissions.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        for (Permission permission : permissions){
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }
}
