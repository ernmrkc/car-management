package com.bist.backendmodule.security;

import com.bist.backendmodule.helpers.JwtUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Custom AuthenticationProvider implementation for JWT token authentication.
 * This provider validates the JWT token and returns an authenticated token if valid.
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    /**
     * Authenticates the user based on the provided JWT token.
     *
     * @param authentication the authentication request object
     * @return a fully authenticated object including credentials if successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String username = JwtUtil.getUsernameFromToken(token);

        // Validate the token and return an authenticated token if valid
        if (username != null && JwtUtil.validateToken(token, username)) {
            return new UsernamePasswordAuthenticationToken(username, null, JwtUtil.getAuthoritiesFromToken(token));
        }
        return null;
    }

    /**
     * Returns true if this AuthenticationProvider supports the indicated Authentication object.
     *
     * @param authentication the class of the authentication object
     * @return true if this AuthenticationProvider supports the indicated Authentication object, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
