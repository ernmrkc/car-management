package com.bist.backendmodule.modules.permission.command.handlers;

import com.bist.backendmodule.exceptions.PermissionAlreadyExistsException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.permission.PermissionRepository;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.validations.PermissionValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/**
 * Command handler for creating a new permission.
 * Validates the permission and checks for duplicates before saving.
 */
@Service
public class CreatePermissionCommandHandler implements Command<Permission, BindingResult, Permission> {
    private final PermissionRepository permissionRepository;
    private final PermissionValidationService permissionValidationService;

    /**
     * Constructs a new CreatePermissionCommandHandler with the specified repository and validation service.
     *
     * @param permissionRepository       Repository for managing permissions
     * @param permissionValidationService Service for validating permissions
     */
    public CreatePermissionCommandHandler(PermissionRepository permissionRepository,
                                          PermissionValidationService permissionValidationService) {
        this.permissionRepository = permissionRepository;
        this.permissionValidationService = permissionValidationService;
    }

    /**
     * Executes the command to create a new permission.
     * Validates the permission and checks if it already exists in the repository before saving.
     *
     * @param permission     The permission to be created
     * @param bindingResult  Binding result for validation
     * @return A ResponseEntity containing the created permission
     */
    @Override
    public ResponseEntity<Permission> execute(Permission permission, BindingResult bindingResult) {
        // Validate Permission
        permissionValidationService.validatePermission(permission, bindingResult, CreatePermissionCommandHandler.class);

        // Check if permission already exists
        Optional<Permission> permissionOptional = permissionRepository.findByName(permission.getName());
        if(permissionOptional.isPresent()){
            throw new PermissionAlreadyExistsException(CreatePermissionCommandHandler.class);
        }

        // Save permission to repository
        permissionRepository.save(permission);

        return ResponseEntity.ok().body(permission);
    }
}
