package com.bist.backendmodule.modules.permission.command.handlers;

import com.bist.backendmodule.exceptions.PermissionNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.permission.PermissionRepository;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for deleting a permission by its name.
 * Checks if the permission exists before deleting it from the repository.
 */
@Service
public class DeletePermissionCommandHandler implements Command<String, Void, Void> {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructs a new DeletePermissionCommandHandler with the specified repositories.
     *
     * @param permissionRepository Repository for managing permissions
     * @param roleRepository       Repository for managing roles
     */
    public DeletePermissionCommandHandler(PermissionRepository permissionRepository,
                                          RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Executes the command to delete a permission by its name.
     * Checks if the permission exists in the repository before deleting.
     *
     * @param name          The name of the permission to be deleted
     * @param bindingResult Binding result for validation (not used in this implementation)
     * @return A ResponseEntity indicating the result of the operation
     */
    @Override
    public ResponseEntity<Void> execute(String name, Void bindingResult) {
        Optional<Permission> permissionOptional = permissionRepository.findByName(name);
        if(permissionOptional.isEmpty()){
            throw new PermissionNotFoundException(DeletePermissionCommandHandler.class);
        }
        Permission permission = permissionOptional.get();
        permissionRepository.delete(permission);
        return ResponseEntity.ok().build();
    }
}
