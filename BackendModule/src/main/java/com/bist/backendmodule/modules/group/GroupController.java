package com.bist.backendmodule.modules.group;

import com.bist.backendmodule.modules.group.command.handlers.CreateGroupCommandHandler;
import com.bist.backendmodule.modules.group.command.handlers.DeleteGroupCommandHandler;
import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.modules.group.query.handlers.GetAllGroupsQueryHandler;
import com.bist.backendmodule.modules.group.query.handlers.GetGroupByIdQueryHandler;
import com.bist.backendmodule.modules.group.query.handlers.GetGroupByNameQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user groups.
 * Provides endpoints for creating, deleting, and retrieving user groups.
 */
@RestController
@RequestMapping("/group")
@Tag(name = "Group Controller", description = "Operations related to user groups")
public class GroupController {
    private final CreateGroupCommandHandler createGroupCommandHandler;
    private final DeleteGroupCommandHandler deleteGroupCommandHandler;
    private final GetGroupByIdQueryHandler getGroupByIdQueryHandler;
    private final GetGroupByNameQueryHandler getGroupByNameQueryHandler;
    private final GetAllGroupsQueryHandler getAllGroupsQueryHandler;

    /**
     * Constructs a new GroupController with the specified command and query handlers.
     *
     * @param createGroupCommandHandler  Handler for creating groups
     * @param deleteGroupCommandHandler  Handler for deleting groups
     * @param getGroupByIdQueryHandler   Handler for retrieving groups by ID
     * @param getGroupByNameQueryHandler Handler for retrieving groups by name
     * @param getAllGroupsQueryHandler   Handler for retrieving all groups
     */
    public GroupController(CreateGroupCommandHandler createGroupCommandHandler,
                           DeleteGroupCommandHandler deleteGroupCommandHandler,
                           GetGroupByIdQueryHandler getGroupByIdQueryHandler,
                           GetGroupByNameQueryHandler getGroupByNameQueryHandler,
                           GetAllGroupsQueryHandler getAllGroupsQueryHandler) {
        this.createGroupCommandHandler = createGroupCommandHandler;
        this.deleteGroupCommandHandler = deleteGroupCommandHandler;
        this.getGroupByIdQueryHandler = getGroupByIdQueryHandler;
        this.getGroupByNameQueryHandler = getGroupByNameQueryHandler;
        this.getAllGroupsQueryHandler = getAllGroupsQueryHandler;
    }

    /**
     * Endpoint for creating a new user group.
     *
     * @param group         The group to create
     * @param bindingResult Binding result for validation
     * @return A ResponseEntity containing the created group
     */
    @PostMapping
    @Operation(summary = "Create a new user group", description = "Creates a new user group with the given details.")
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group, BindingResult bindingResult) {
        return createGroupCommandHandler.execute(group, bindingResult);
    }

    /**
     * Endpoint for deleting a user group by name.
     *
     * @param name The name of the group to delete
     * @return A ResponseEntity with HTTP status 200 (OK)
     */
    @DeleteMapping("/{name}")
    @Operation(summary = "Delete a user group", description = "Deletes an existing user group by name.")
    public ResponseEntity<Void> deleteGroup(@PathVariable String name) {
        return deleteGroupCommandHandler.execute(name, null);
    }

    /**
     * Endpoint for retrieving a user group by ID.
     *
     * @param id The ID of the group to retrieve
     * @return A ResponseEntity containing the retrieved group
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get a user group", description = "Gets an existing user group by id.")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        return getGroupByIdQueryHandler.execute(id);
    }

    /**
     * Endpoint for retrieving a user group by name.
     *
     * @param name The name of the group to retrieve
     * @return A ResponseEntity containing the retrieved group
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get a user group", description = "Gets an existing user group by name")
    public ResponseEntity<Group> getGroupByName(@PathVariable String name) {
        return getGroupByNameQueryHandler.execute(name);
    }

    /**
     * Endpoint for retrieving all user groups.
     *
     * @return A ResponseEntity containing a list of all groups
     */
    @GetMapping
    @Operation(summary = "Get all user groups", description = "Gets all user groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        return getAllGroupsQueryHandler.execute(null);
    }
}
