package com.bist.backendmodule.modules.role.command.handlers;

import com.bist.backendmodule.exceptions.RoleNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.permission.query.handlers.GetPermissionByIdQueryHandler;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import com.bist.backendmodule.modules.role.models.UpdatePermissionsCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for removing permissions from an existing role.
 */
@Service
public class RemovePermissionCommandHandler implements Command<UpdatePermissionsCommand, Void, Role> {
    private final RoleRepository roleRepository;
    private final GetPermissionByIdQueryHandler getPermissionByIdQueryHandler;

    /**
     * Constructs a new RemovePermissionCommandHandler with the specified role repository and permission query handler.
     *
     * @param roleRepository                Role repository for accessing role data
     * @param getPermissionByIdQueryHandler Query handler for retrieving permissions by ID
     */
    public RemovePermissionCommandHandler(RoleRepository roleRepository,
                                          GetPermissionByIdQueryHandler getPermissionByIdQueryHandler) {
        this.roleRepository = roleRepository;
        this.getPermissionByIdQueryHandler = getPermissionByIdQueryHandler;
    }

    /**
     * Executes the command to remove permissions from a role.
     * Finds the role by ID and removes the specified permissions if they exist.
     *
     * @param updatePermissionsCommand Command containing the role ID and permission IDs to be removed
     * @param bindingResult            Not used in this command
     * @return ResponseEntity with the updated role if the removal is successful
     * @throws RoleNotFoundException if the role with the specified ID is not found
     */
    @Override
    public ResponseEntity<Role> execute(UpdatePermissionsCommand updatePermissionsCommand, Void bindingResult) {
        Optional<Role> roleOptional = roleRepository.findById(updatePermissionsCommand.getRoleId());
        if (roleOptional.isEmpty()) {
            throw new RoleNotFoundException(RemovePermissionCommandHandler.class);
        }
        Role role = roleOptional.get();
        for (Long permissionId : updatePermissionsCommand.getPermissionIds()) {
            Permission permission = getPermissionByIdQueryHandler.execute(permissionId).getBody();
            role.getPermissions().remove(permission);
        }
        roleRepository.save(role);
        return ResponseEntity.ok().body(role);
    }
}
