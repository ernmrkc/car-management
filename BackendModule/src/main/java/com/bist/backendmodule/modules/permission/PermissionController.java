package com.bist.backendmodule.modules.permission;

import com.bist.backendmodule.modules.permission.command.handlers.CreatePermissionCommandHandler;
import com.bist.backendmodule.modules.permission.command.handlers.DeletePermissionCommandHandler;
import com.bist.backendmodule.modules.permission.models.Permission;
import com.bist.backendmodule.modules.permission.query.handlers.GetAllPermissionsQueryHandler;
import com.bist.backendmodule.modules.permission.query.handlers.GetPermissionByIdQueryHandler;
import com.bist.backendmodule.modules.permission.query.handlers.GetPermissionByNameQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user permissions.
 * Provides endpoints for creating, deleting, and retrieving permissions.
 */
@RestController
@RequestMapping("/permission")
@Tag(name = "Permission Controller", description = "Operations related to user permissions")
public class PermissionController {
    private final CreatePermissionCommandHandler createPermissionCommandHandler;
    private final DeletePermissionCommandHandler deletePermissionCommandHandler;
    private final GetPermissionByNameQueryHandler getPermissionByNameQueryHandler;
    private final GetPermissionByIdQueryHandler getPermissionByIdQueryHandler;
    private final GetAllPermissionsQueryHandler getAllPermissionsQueryHandler;

    /**
     * Constructs a new PermissionController with the specified handlers.
     *
     * @param createPermissionCommandHandler  Handler for creating permissions
     * @param deletePermissionCommandHandler  Handler for deleting permissions
     * @param getPermissionByNameQueryHandler Handler for retrieving permissions by name
     * @param getPermissionByIdQueryHandler   Handler for retrieving permissions by id
     * @param getAllPermissionsQueryHandler   Handler for retrieving all permissions
     */
    public PermissionController(CreatePermissionCommandHandler createPermissionCommandHandler,
                                DeletePermissionCommandHandler deletePermissionCommandHandler,
                                GetPermissionByNameQueryHandler getPermissionByNameQueryHandler,
                                GetPermissionByIdQueryHandler getPermissionByIdQueryHandler,
                                GetAllPermissionsQueryHandler getAllPermissionsQueryHandler) {
        this.createPermissionCommandHandler = createPermissionCommandHandler;
        this.deletePermissionCommandHandler = deletePermissionCommandHandler;
        this.getPermissionByNameQueryHandler = getPermissionByNameQueryHandler;
        this.getPermissionByIdQueryHandler = getPermissionByIdQueryHandler;
        this.getAllPermissionsQueryHandler = getAllPermissionsQueryHandler;
    }

    /**
     * Creates a new user permission with the given details.
     *
     * @param permission    The details of the permission to create
     * @param bindingResult Binding result for validation
     * @return ResponseEntity containing the created permission
     */
    @PostMapping
    @Operation(summary = "Create a new user permission", description = "Creates a new user permission with given details.")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission, BindingResult bindingResult) {
        return createPermissionCommandHandler.execute(permission, bindingResult);
    }

    /**
     * Deletes an existing user permission by name.
     *
     * @param name The name of the permission to delete
     * @return ResponseEntity with status code
     */
    @DeleteMapping("/{name}")
    @Operation(summary = "Delete a user permission", description = "Deletes an existing user permission by name.")
    public ResponseEntity<Void> deletePermission(@PathVariable String name) {
        return deletePermissionCommandHandler.execute(name, null);
    }

    /**
     * Retrieves an existing user permission by name.
     *
     * @param name The name of the permission to retrieve
     * @return ResponseEntity containing the retrieved permission
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get a user permission", description = "Gets an existing user permission by name.")
    public ResponseEntity<Permission> getPermissionByName(@PathVariable String name) {
        return getPermissionByNameQueryHandler.execute(name);
    }

    /**
     * Retrieves an existing user permission by id.
     *
     * @param id The id of the permission to retrieve
     * @return ResponseEntity containing the retrieved permission
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get a user permission", description = "Gets an existing user permission by id.")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        return getPermissionByIdQueryHandler.execute(id);
    }

    /**
     * Retrieves all user permissions.
     *
     * @return ResponseEntity containing a list of all permissions
     */
    @GetMapping
    @Operation(summary = "Get all user permissions", description = "Gets all user permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return getAllPermissionsQueryHandler.execute(null);
    }
}
