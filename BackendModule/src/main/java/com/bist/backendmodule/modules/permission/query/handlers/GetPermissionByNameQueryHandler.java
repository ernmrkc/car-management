package com.bist.backendmodule.modules.permission.query.handlers;

import com.bist.backendmodule.exceptions.PermissionNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.permission.PermissionRepository;
import com.bist.backendmodule.modules.permission.models.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a permission by its name.
 * Fetches the permission with the specified name from the repository.
 */
@Service
public class GetPermissionByNameQueryHandler implements Query<String, Permission> {
    private final PermissionRepository permissionRepository;

    /**
     * Constructs a new GetPermissionByNameQueryHandler with the specified repository.
     *
     * @param permissionRepository Repository for managing permissions
     */
    public GetPermissionByNameQueryHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Executes the query to retrieve a permission by its name.
     * Fetches the permission with the specified name from the repository.
     *
     * @param name The name of the permission to retrieve
     * @return A ResponseEntity containing the permission with the specified name
     * @throws PermissionNotFoundException if the permission with the specified name is not found
     */
    @Override
    public ResponseEntity<Permission> execute(String name) {
        Optional<Permission> permissionOptional = permissionRepository.findByName(name);
        if (permissionOptional.isEmpty()) {
            throw new PermissionNotFoundException(GetPermissionByNameQueryHandler.class);
        }
        Permission permission = permissionOptional.get();
        return ResponseEntity.ok().body(permission);
    }
}
