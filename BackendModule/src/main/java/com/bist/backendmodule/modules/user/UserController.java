package com.bist.backendmodule.modules.user;

import com.bist.backendmodule.modules.user.command.handlers.*;
import com.bist.backendmodule.modules.user.models.UpdateGroupCommand;
import com.bist.backendmodule.modules.user.models.UpdateRoleCommand;
import com.bist.backendmodule.modules.user.models.User;
import com.bist.backendmodule.modules.user.models.UserCreateDTO;
import com.bist.backendmodule.modules.user.query.handlers.GetAllUsersQueryHandler;
import com.bist.backendmodule.modules.user.query.handlers.GetUserByIdQueryHandler;
import com.bist.backendmodule.modules.user.query.handlers.GetUserByUsernameQueryHandler;
import com.bist.backendmodule.security.models.LoginRequest;
import com.bist.backendmodule.security.models.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing users. Provides endpoints for user-related operations.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Operations related to user")
public class UserController {
    private final CreateUserCommandHandler createUserCommandHandler;
    private final LoginUserCommandHandler loginUserCommandHandler;
    private final AddRoleCommandHandler addRoleCommandHandler;
    private final AddGroupCommandHandler addGroupCommandHandler;
    private final RemoveRoleCommandHandler removeRoleCommandHandler;
    private final RemoveGroupCommandHandler removeGroupCommandHandler;
    private final DeleteUserCommandHandler deleteUserCommandHandler;
    private final GetUserByUsernameQueryHandler getUserByUsernameQueryHandler;
    private final GetUserByIdQueryHandler getUserByIdQueryHandler;
    private final GetAllUsersQueryHandler getAllUsersQueryHandler;

    /**
     * Constructs a new UserController with the specified handlers.
     *
     * @param createUserCommandHandler The handler for creating a user.
     * @param loginUserCommandHandler The handler for logging in a user.
     * @param addRoleCommandHandler The handler for adding roles to a user.
     * @param addGroupCommandHandler The handler for adding groups to a user.
     * @param removeRoleCommandHandler The handler for removing roles from a user.
     * @param removeGroupCommandHandler The handler for removing groups from a user.
     * @param deleteUserCommandHandler The handler for deleting a user.
     * @param getUserByUsernameQueryHandler The handler for getting a user by username.
     * @param getUserByIdQueryHandler The handler for getting a user by ID.
     * @param getAllUsersQueryHandler The handler for getting all users.
     */
    public UserController(CreateUserCommandHandler createUserCommandHandler,
                          LoginUserCommandHandler loginUserCommandHandler,
                          AddRoleCommandHandler addRoleCommandHandler,
                          AddGroupCommandHandler addGroupCommandHandler,
                          RemoveRoleCommandHandler removeRoleCommandHandler,
                          RemoveGroupCommandHandler removeGroupCommandHandler,
                          DeleteUserCommandHandler deleteUserCommandHandler,
                          GetUserByUsernameQueryHandler getUserByUsernameQueryHandler,
                          GetUserByIdQueryHandler getUserByIdQueryHandler,
                          GetAllUsersQueryHandler getAllUsersQueryHandler) {
        this.createUserCommandHandler = createUserCommandHandler;
        this.loginUserCommandHandler = loginUserCommandHandler;
        this.addRoleCommandHandler = addRoleCommandHandler;
        this.addGroupCommandHandler = addGroupCommandHandler;
        this.removeRoleCommandHandler = removeRoleCommandHandler;
        this.removeGroupCommandHandler = removeGroupCommandHandler;
        this.deleteUserCommandHandler = deleteUserCommandHandler;
        this.getUserByUsernameQueryHandler = getUserByUsernameQueryHandler;
        this.getUserByIdQueryHandler = getUserByIdQueryHandler;
        this.getAllUsersQueryHandler = getAllUsersQueryHandler;
    }

    /**
     * Creates a new user.
     *
     * @param userCreateDTO The details of the user to be created.
     * @param bindingResult The result of binding the request parameters.
     * @return The created user.
     */
    @PreAuthorize("hasAuthority('PERMISSON_CREATE_USER')")
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with given details.")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO, BindingResult bindingResult){
        return createUserCommandHandler.execute(userCreateDTO, bindingResult);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param loginRequest The login request containing username and password.
     * @param bindingResult The result of binding the request parameters.
     * @return The JWT token if the credentials are valid.
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user with their username and password, and returns a JWT token if the credentials are valid.")
    public ResponseEntity<TokenResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        return loginUserCommandHandler.execute(loginRequest, bindingResult);
    }

    /**
     * Adds roles to a user.
     *
     * @param updateRoleCommand The command containing user ID and role IDs.
     * @return The updated user.
     */
    @PutMapping("/add-role")
    @Operation(summary = "Add roles to user", description = "Adds roles to the corresponding user.")
    public ResponseEntity<User> addRolesToUser(@RequestBody UpdateRoleCommand updateRoleCommand){
        return addRoleCommandHandler.execute(updateRoleCommand, null);
    }

    /**
     * Adds groups to a user.
     *
     * @param updateGroupCommand The command containing user ID and group IDs.
     * @return The updated user.
     */
    @PutMapping("/add-group")
    @Operation(summary = "Add groups to user", description = "Adds groups to the corresponding user.")
    public ResponseEntity<User> addGroupsToUser(@RequestBody UpdateGroupCommand updateGroupCommand){
        return addGroupCommandHandler.execute(updateGroupCommand, null);
    }

    /**
     * Removes roles from a user.
     *
     * @param updateRoleCommand The command containing user ID and role IDs.
     * @return The updated user.
     */
    @PutMapping("/remove-role")
    @Operation(summary = "Remove roles from user", description = "Removes roles from the corresponding user.")
    public ResponseEntity<User> removeRolesFromUser(@RequestBody UpdateRoleCommand updateRoleCommand){
        return removeRoleCommandHandler.execute(updateRoleCommand, null);
    }

    /**
     * Removes groups from a user.
     *
     * @param updateGroupCommand The command containing user ID and group IDs.
     * @return The updated user.
     */
    @PutMapping("/remove-group")
    @Operation(summary = "Remove groups from user", description = "Removes groups from the corresponding user.")
    public ResponseEntity<User> removeGroupsFromUser(@RequestBody UpdateGroupCommand updateGroupCommand){
        return removeGroupCommandHandler.execute(updateGroupCommand, null);
    }

    /**
     * Deletes a user by username.
     *
     * @param username The username of the user to be deleted.
     * @return A response entity indicating the result of the operation.
     */
    @PreAuthorize("hasAuthority('PERMISSON_DELETE_USER')")
    @DeleteMapping("/{username}")
    @Operation(summary = "Delete a user", description = "Deletes an existing user by name.")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        return deleteUserCommandHandler.execute(username, null);
    }

    /**
     * Gets a user by username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the specified username.
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "Get a user", description = "Gets a user by username.")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        return getUserByUsernameQueryHandler.execute(username);
    }

    /**
     * Gets a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID.
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get a user", description = "Gets a user by id.")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return getUserByIdQueryHandler.execute(id);
    }

    /**
     * Gets all users.
     *
     * @return A list of all users.
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Gets all users")
    public ResponseEntity<List<User>> getAllUsers(){
        return getAllUsersQueryHandler.execute(null);
    }

    /**
     * Method for checking authorization with the specified permission.
     *
     * @return A success message if the user has the required permission.
     */
    @PreAuthorize("hasAuthority('PERMISSON_CREATE_USER')")
    @GetMapping("/authentication")
    public String methodAuth(){
        return "Success";
    }
}
