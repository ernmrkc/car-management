package com.bist.backendmodule.services;

import com.bist.backendmodule.modules.user.models.User;
import com.bist.backendmodule.modules.user.query.handlers.GetUserByUsernameQueryHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * CustomUserDetailsService is a custom implementation of the UserDetailsService interface.
 * This class is used to load user-specific data during the authentication process.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final GetUserByUsernameQueryHandler getUserByUsernameQueryHandler;

    /**
     * Constructs a new CustomUserDetailsService with the specified GetUserByUsernameQueryHandler.
     *
     * @param getUserByUsernameQueryHandler the handler to get user information by username
     */
    public CustomUserDetailsService(GetUserByUsernameQueryHandler getUserByUsernameQueryHandler) {
        this.getUserByUsernameQueryHandler = getUserByUsernameQueryHandler;
    }

    /**
     * Locates the user based on the username.
     *
     * @param username the username identifying the user whose data is required
     * @return a fully populated user record (never null)
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsernameQueryHandler.execute(username).getBody();
        assert user != null;
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @param user the user whose authorities are required
     * @return the authorities granted to the user
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }
}
