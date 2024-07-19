package com.bist.backendmodule.services;

import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.security.models.TokenRequest;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * CustomPermissionEvaluator is a custom implementation of the PermissionEvaluator interface.
 * This class is used to evaluate whether a user has a specific permission.
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * Evaluates whether the user has the specified permission.
     *
     * @param authentication     the authentication object representing the user
     * @param targetDomainObject the domain object for which permissions should be checked
     * @param permission         the permission to check
     * @return true if the user has the specified permission, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || permission == null) {
            return false;
        }

        String permissionName = permission.toString();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof TokenRequest) {
            List<String> permissions = ((TokenRequest) userDetails).getPermissions().stream()
                    .map(Permission::getAuthority)
                    .toList();
            return permissions.contains(permissionName);
        }
        return false;
    }

    /**
     * Evaluates whether the user has the specified permission for a target identified by ID and type.
     *
     * @param authentication the authentication object representing the user
     * @param targetId       the ID of the target object
     * @param targetType     the type of the target object
     * @param permission     the permission to check
     * @return true if the user has the specified permission, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, null, permission);
    }
}
