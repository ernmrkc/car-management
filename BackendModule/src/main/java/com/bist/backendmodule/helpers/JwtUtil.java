package com.bist.backendmodule.helpers;

import com.bist.backendmodule.exceptions.*;
import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.security.models.TokenRequest;
import com.bist.backendmodule.security.models.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for generating and validating JWT tokens.
 */
public class JwtUtil {
    private static final SecretKey KEY = KeyGenUtil.getKey();
    private static final long EXP_TIME_10_DAYS = 864_000_000;

    /**
     * Generates a JWT token based on the provided TokenRequest.
     *
     * @param tokenRequest The request containing user details
     * @return A TokenResponse containing the username and generated token
     */
    public static TokenResponse generateToken(TokenRequest tokenRequest) {
        String token = Jwts.builder()
                .claim("sub", tokenRequest.getUsername())
                .claim("id", tokenRequest.getUserId())
                .claim("roles", tokenRequest.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("permissions", tokenRequest.getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toList()))
                .claim("groups", tokenRequest.getGroups().stream().map(Group::getName).collect(Collectors.toList()))
                .claim("iat", new Date(System.currentTimeMillis()))
                .claim("exp", new Date(System.currentTimeMillis() + EXP_TIME_10_DAYS))
                .signWith(KEY)
                .compact();

        return new TokenResponse(tokenRequest.getUsername(), token);
    }

    /**
     * Retrieves all claims from the given JWT token.
     *
     * @param token The JWT token
     * @return The claims contained in the token
     * @throws InvalidTokenSignatureException if the token signature is invalid
     * @throws TokenValidationException       if the token validation fails
     */
    public static Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException exception) {
            throw new InvalidTokenSignatureException("Invalid token signature..", exception, JwtUtil.class);
        } catch (Exception exception) {
            throw new TokenValidationException("Token validation failed:", exception, JwtUtil.class);
        }
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token
     * @return The username
     */
    public static String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).get("sub", String.class);
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token
     * @return True if the token is expired, false otherwise
     */
    public static boolean isTokenExpired(String token) {
        try {
            Date expiration = getAllClaimsFromToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Extracts the authorities (roles and permissions) from the JWT token.
     *
     * @param token The JWT token
     * @return A list of GrantedAuthority
     */
    public static List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        List<String> roles = getRolesFromToken(token);
        List<String> permissions = getPermissionsFromToken(token);
        return Stream.concat(roles.stream(), permissions.stream())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Extracts the roles from the JWT token.
     *
     * @param token The JWT token
     * @return A list of roles
     * @throws InvalidRolesFormatException if the roles format is invalid
     */
    private static List<String> getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object rolesObject = claims.get("roles");
        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .filter(item -> item instanceof String)
                    .map(item -> (String) item)
                    .toList();
        }
        throw new InvalidRolesFormatException("Invalid roles format..", JwtUtil.class);
    }

    /**
     * Extracts the permissions from the JWT token.
     *
     * @param token The JWT token
     * @return A list of permissions
     * @throws InvalidPermissionsFormatException if the permissions format is invalid
     */
    private static List<String> getPermissionsFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object permissionsObject = claims.get("permissions");
        if (permissionsObject instanceof List<?>) {
            return ((List<?>) permissionsObject).stream()
                    .filter(item -> item instanceof String)
                    .map(item -> (String) item)
                    .toList();
        }
        throw new InvalidPermissionsFormatException("Invalid permission format..", JwtUtil.class);
    }

    /**
     * Extracts the groups from the JWT token.
     *
     * @param token The JWT token
     * @return A list of groups
     * @throws InvalidGroupsFormatException if the groups format is invalid
     */
    private static List<String> getGroupsFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object groupsObject = claims.get("groups");
        if (groupsObject instanceof List<?>) {
            return ((List<?>) groupsObject).stream()
                    .filter(item -> item instanceof String)
                    .map(item -> (String) item)
                    .toList();
        }
        throw new InvalidGroupsFormatException("Invalid groups format..", JwtUtil.class);
    }

    /**
     * Validates the JWT token against the provided username.
     *
     * @param token The JWT token
     * @param username The username to validate against
     * @return True if the token is valid and not expired, false otherwise
     */
    public static boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}
