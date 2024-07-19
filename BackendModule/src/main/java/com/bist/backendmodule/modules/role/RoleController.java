package com.bist.backendmodule.modules.role;

import com.bist.backendmodule.modules.role.command.handlers.AddPermissionCommandHandler;
import com.bist.backendmodule.modules.role.command.handlers.CreateRoleCommandHandler;
import com.bist.backendmodule.modules.role.command.handlers.DeleteRoleCommandHandler;
import com.bist.backendmodule.modules.role.command.handlers.RemovePermissionCommandHandler;
import com.bist.backendmodule.modules.role.models.UpdatePermissionsCommand;
import com.bist.backendmodule.modules.role.models.Role;
import com.bist.backendmodule.modules.role.models.RoleCreateDTO;
import com.bist.backendmodule.modules.role.query.handlers.GetAllRolesQueryHandler;
import com.bist.backendmodule.modules.role.query.handlers.GetRoleByIdQueryHandler;
import com.bist.backendmodule.modules.role.query.handlers.GetRoleByNameQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user roles.
 * Provides endpoints for creating, updating, deleting, and retrieving user roles.
 */
@RestController
@RequestMapping("/role")
@Tag(name = "Role Controller", description = "Operations related to user roles")
public class RoleController {
    private final CreateRoleCommandHandler createRoleCommandHandler;
    private final AddPermissionCommandHandler addPermissionCommandHandler;
    private final RemovePermissionCommandHandler removePermissionCommandHandler;
    private final DeleteRoleCommandHandler deleteRoleCommandHandler;
    private final GetRoleByNameQueryHandler getRoleByNameQueryHandler;
    private final GetRoleByIdQueryHandler getRoleByIdQueryHandler;
    private final GetAllRolesQueryHandler getAllRolesQueryHandler;

    /**
     * Constructs a new RoleController with the specified command and query handlers.
     *
     * @param createRoleCommandHandler       Handler for creating roles
     * @param addPermissionCommandHandler    Handler for adding permissions to roles
     * @param removePermissionCommandHandler Handler for removing permissions from roles
     * @param deleteRoleCommandHandler       Handler for deleting roles
     * @param getRoleByNameQueryHandler      Handler for retrieving roles by name
     * @param getRoleByIdQueryHandler        Handler for retrieving roles by ID
     * @param getAllRolesQueryHandler        Handler for retrieving all roles
     */
    public RoleController(CreateRoleCommandHandler createRoleCommandHandler,
                          AddPermissionCommandHandler addPermissionCommandHandler,
                          RemovePermissionCommandHandler removePermissionCommandHandler,
                          DeleteRoleCommandHandler deleteRoleCommandHandler,
                          GetRoleByNameQueryHandler getRoleByNameQueryHandler,
                          GetRoleByIdQueryHandler getRoleByIdQueryHandler,
                          GetAllRolesQueryHandler getAllRolesQueryHandler) {
        this.createRoleCommandHandler = createRoleCommandHandler;
        this.addPermissionCommandHandler = addPermissionCommandHandler;
        this.removePermissionCommandHandler = removePermissionCommandHandler;
        this.deleteRoleCommandHandler = deleteRoleCommandHandler;
        this.getRoleByNameQueryHandler = getRoleByNameQueryHandler;
        this.getRoleByIdQueryHandler = getRoleByIdQueryHandler;
        this.getAllRolesQueryHandler = getAllRolesQueryHandler;
    }

    /**
     * Creates a new user role with the given details.
     *
     * @param roleCreateDTO The role creation DTO containing the role details
     * @param bindingResult The binding result for validation
     * @return ResponseEntity with the created role
     */
    @PostMapping
    @Operation(summary = "Create a new user role", description = "Creates a new user role with given details.")
    public ResponseEntity<Role> createRole(@Valid @RequestBody RoleCreateDTO roleCreateDTO, BindingResult bindingResult) {
        return createRoleCommandHandler.execute(roleCreateDTO, bindingResult);
    }

    /**
     * Adds permissions to the corresponding role.
     *
     * @param updatePermissionsCommand The command containing the role ID and permissions to add
     * @return ResponseEntity with the updated role
     */
    @PutMapping("/add-permission")
    @Operation(summary = "Add permissions to role", description = "Adds permissions to the corresponding role.")
    public ResponseEntity<Role> addPermissionToRole(@RequestBody UpdatePermissionsCommand updatePermissionsCommand) {
        return addPermissionCommandHandler.execute(updatePermissionsCommand, null);
    }

    /**
     * Removes permissions from the corresponding role.
     *
     * @param updatePermissionsCommand The command containing the role ID and permissions to remove
     * @return ResponseEntity with the updated role
     */
    @PutMapping("/remove-permission")
    @Operation(summary = "Remove permissions from role", description = "Removes permissions from the corresponding role.")
    public ResponseEntity<Role> removePermissionFromRole(@RequestBody UpdatePermissionsCommand updatePermissionsCommand) {
        return removePermissionCommandHandler.execute(updatePermissionsCommand, null);
    }

    /**
     * Deletes an existing user role by name.
     *
     * @param name The name of the role to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{name}")
    @Operation(summary = "Delete a user role", description = "Deletes an existing user role by name.")
    public ResponseEntity<Void> deleteRole(@PathVariable String name) {
        return deleteRoleCommandHandler.execute(name, null);
    }

    /**
     * Gets an existing user role by name.
     *
     * @param name The name of the role to retrieve
     * @return ResponseEntity with the role data
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get a user role", description = "Gets an existing user role by name.")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return getRoleByNameQueryHandler.execute(name);
    }

    /**
     * Gets an existing user role by ID.
     *
     * @param id The ID of the role to retrieve
     * @return ResponseEntity with the role data
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get a user role", description = "Gets an existing user role by id.")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return getRoleByIdQueryHandler.execute(id);
    }

    /**
     * Gets all user roles.
     *
     * @return ResponseEntity with the list of all roles
     */
    @GetMapping
    @Operation(summary = "Get all user roles", description = "Gets all user roles.")
    public ResponseEntity<List<Role>> getAllRoles() {
        return getAllRolesQueryHandler.execute(null);
    }
}
