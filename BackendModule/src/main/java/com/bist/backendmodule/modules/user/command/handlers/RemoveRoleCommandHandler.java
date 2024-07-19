package com.bist.backendmodule.modules.user.command.handlers;

import com.bist.backendmodule.exceptions.UserNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.role.models.Role;
import com.bist.backendmodule.modules.role.query.handlers.GetRoleByIdQueryHandler;
import com.bist.backendmodule.modules.user.UserRepository;
import com.bist.backendmodule.modules.user.models.UpdateRoleCommand;
import com.bist.backendmodule.modules.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for removing roles from a user.
 */
@Service
public class RemoveRoleCommandHandler implements Command<UpdateRoleCommand, Void, User> {
    private final UserRepository userRepository;
    private final GetRoleByIdQueryHandler getRoleByIdQueryHandler;

    /**
     * Constructs a new RemoveRoleCommandHandler with the specified dependencies.
     *
     * @param userRepository          The repository to manage users
     * @param getRoleByIdQueryHandler The handler to get a role by its ID
     */
    public RemoveRoleCommandHandler(UserRepository userRepository,
                                    GetRoleByIdQueryHandler getRoleByIdQueryHandler) {
        this.userRepository = userRepository;
        this.getRoleByIdQueryHandler = getRoleByIdQueryHandler;
    }

    /**
     * Executes the command to remove roles from a user.
     *
     * @param updateRoleCommand The command containing the user ID and role IDs to remove
     * @param bindingResult     The binding result for validation errors (not used in this implementation)
     * @return ResponseEntity containing the updated User
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public ResponseEntity<User> execute(UpdateRoleCommand updateRoleCommand, Void bindingResult) {
        Optional<User> userOptional = userRepository.findById(updateRoleCommand.getUserId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(RemoveRoleCommandHandler.class);
        }
        User user = userOptional.get();
        for (Long roleId : updateRoleCommand.getRoleIds()) {
            Role role = getRoleByIdQueryHandler.execute(roleId).getBody();
            user.getRoles().remove(role);
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
