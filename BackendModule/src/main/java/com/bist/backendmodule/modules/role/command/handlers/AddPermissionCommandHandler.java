package com.bist.backendmodule.modules.role.command.handlers;

import com.bist.backendmodule.exceptions.RoleNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.permission.query.handlers.GetPermissionByIdQueryHandler;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.UpdatePermissionsCommand;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for adding permissions to a role.
 */
@Service
public class AddPermissionCommandHandler implements Command<UpdatePermissionsCommand, Void, Role> {
    private final RoleRepository roleRepository;
    private final GetPermissionByIdQueryHandler getPermissionByIdQueryHandler;

    /**
     * Constructs a new AddPermissionCommandHandler with the specified repositories and handlers.
     *
     * @param roleRepository                Role repository for accessing role data
     * @param getPermissionByIdQueryHandler Handler for retrieving permissions by id
     */
    public AddPermissionCommandHandler(RoleRepository roleRepository,
                                       GetPermissionByIdQueryHandler getPermissionByIdQueryHandler) {
        this.roleRepository = roleRepository;
        this.getPermissionByIdQueryHandler = getPermissionByIdQueryHandler;
    }

    /**
     * Executes the command to add permissions to a role.
     * Retrieves the role by id, adds the specified permissions, and saves the updated role.
     *
     * @param updatePermissionsCommand Command containing the role id and permission ids to be added
     * @param bindingResult            Binding result for validation (not used in this implementation)
     * @return ResponseEntity containing the updated role
     * @throws RoleNotFoundException if the role is not found
     */
    @Override
    public ResponseEntity<Role> execute(UpdatePermissionsCommand updatePermissionsCommand, Void bindingResult) {
        Optional<Role> roleOptional = roleRepository.findById(updatePermissionsCommand.getRoleId());
        if (roleOptional.isEmpty()) {
            throw new RoleNotFoundException(AddPermissionCommandHandler.class);
        }
        Role role = roleOptional.get();
        for (Long permissionId : updatePermissionsCommand.getPermissionIds()) {
            Permission permission = getPermissionByIdQueryHandler.execute(permissionId).getBody();
            if (!role.getPermissions().contains(permission)) {
                role.getPermissions().add(permission);
            }
        }
        roleRepository.save(role);
        return ResponseEntity.ok().body(role);
    }
}
