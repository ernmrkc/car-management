package com.bist.backendmodule.modules.permission.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.permission.PermissionRepository;
import com.bist.backendmodule.modules.permission.models.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Query handler for retrieving all permissions.
 * Fetches all permissions from the repository and returns them.
 */
@Service
public class GetAllPermissionsQueryHandler implements Query<Void, List<Permission>> {
    private final PermissionRepository permissionRepository;

    /**
     * Constructs a new GetAllPermissionsQueryHandler with the specified repository.
     *
     * @param permissionRepository Repository for managing permissions
     */
    public GetAllPermissionsQueryHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Executes the query to retrieve all permissions.
     * Fetches all permissions from the repository.
     *
     * @param input Unused input parameter for this query
     * @return A ResponseEntity containing the list of all permissions
     */
    @Override
    public ResponseEntity<List<Permission>> execute(Void input) {
        List<Permission> permissionList = permissionRepository.findAll();
        return ResponseEntity.ok().body(permissionList);
    }
}
