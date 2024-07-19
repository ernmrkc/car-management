package com.bist.backendmodule.modules.permission.query.handlers;

import com.bist.backendmodule.exceptions.PermissionNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.permission.PermissionRepository;
import com.bist.backendmodule.modules.permission.models.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a permission by its ID.
 * Fetches the permission with the specified ID from the repository.
 */
@Service
public class GetPermissionByIdQueryHandler implements Query<Long, Permission> {
    private final PermissionRepository permissionRepository;

    /**
     * Constructs a new GetPermissionByIdQueryHandler with the specified repository.
     *
     * @param permissionRepository Repository for managing permissions
     */
    public GetPermissionByIdQueryHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Executes the query to retrieve a permission by its ID.
     * Fetches the permission with the specified ID from the repository.
     *
     * @param id The ID of the permission to retrieve
     * @return A ResponseEntity containing the permission with the specified ID
     * @throws PermissionNotFoundException if the permission with the specified ID is not found
     */
    @Override
    public ResponseEntity<Permission> execute(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isEmpty()) {
            throw new PermissionNotFoundException(GetPermissionByIdQueryHandler.class);
        }
        Permission permission = permissionOptional.get();
        return ResponseEntity.ok().body(permission);
    }
}
