package com.bist.backendmodule.modules.role.command.handlers;

import com.bist.backendmodule.exceptions.RoleAlreadyExistsException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.permission.query.handlers.GetPermissionByIdQueryHandler;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import com.bist.backendmodule.modules.role.models.RoleCreateDTO;
import com.bist.backendmodule.validations.RoleValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

/**
 * Command handler for creating a new role.
 */
@Service
public class CreateRoleCommandHandler implements Command<RoleCreateDTO, BindingResult, Role> {
    private final RoleRepository roleRepository;
    private final RoleValidationService roleValidationService;
    private final GetPermissionByIdQueryHandler getPermissionByIdQueryHandler;

    /**
     * Constructs a new CreateRoleCommandHandler with the specified repositories and handlers.
     *
     * @param roleRepository                Role repository for accessing role data
     * @param roleValidationService         Service for validating role data
     * @param getPermissionByIdQueryHandler Handler for retrieving permissions by id
     */
    public CreateRoleCommandHandler(RoleRepository roleRepository,
                                    RoleValidationService roleValidationService,
                                    GetPermissionByIdQueryHandler getPermissionByIdQueryHandler) {
        this.roleRepository = roleRepository;
        this.roleValidationService = roleValidationService;
        this.getPermissionByIdQueryHandler = getPermissionByIdQueryHandler;
    }

    /**
     * Executes the command to create a new role.
     * Validates the role data, checks for name uniqueness, sets permissions, and saves the role.
     *
     * @param roleCreateDTO DTO containing the role details
     * @param bindingResult Binding result for validation
     * @return ResponseEntity containing the created role
     * @throws RoleAlreadyExistsException if a role with the same name already exists
     */
    @Override
    public ResponseEntity<Role> execute(RoleCreateDTO roleCreateDTO, BindingResult bindingResult) {
        // Check if name already exists
        Optional<Role> roleOptional = roleRepository.findByName(roleCreateDTO.getName());
        if (roleOptional.isPresent()) {
            throw new RoleAlreadyExistsException(CreateRoleCommandHandler.class);
        }

        // Create role
        Role role = new Role();
        role.setName(roleCreateDTO.getName());

        // Set permissions if applicable
        if (!roleCreateDTO.getPermissionIds().isEmpty()) {
            List<Permission> permissions = new ArrayList<>();
            for (Long permissionId : roleCreateDTO.getPermissionIds()) {
                Permission permission = getPermissionByIdQueryHandler.execute(permissionId).getBody();
                permissions.add(permission);
            }
            role.setPermissions(permissions);
        }

        // Validate Role
        roleValidationService.validateRole(role, bindingResult, CreateRoleCommandHandler.class);

        // Save role to repository
        roleRepository.save(role);
        return ResponseEntity.ok().body(role);
    }
}
