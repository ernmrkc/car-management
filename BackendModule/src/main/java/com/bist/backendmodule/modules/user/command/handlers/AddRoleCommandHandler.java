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
 * Command handler for adding roles to a user.
 */
@Service
public class AddRoleCommandHandler implements Command<UpdateRoleCommand, Void, User> {
    private final UserRepository userRepository;
    private final GetRoleByIdQueryHandler getRoleByIdQueryHandler;

    /**
     * Constructs a new AddRoleCommandHandler with the specified repositories and query handlers.
     *
     * @param userRepository          The repository for user entities
     * @param getRoleByIdQueryHandler The query handler for retrieving roles by ID
     */
    public AddRoleCommandHandler(UserRepository userRepository,
                                 GetRoleByIdQueryHandler getRoleByIdQueryHandler) {
        this.userRepository = userRepository;
        this.getRoleByIdQueryHandler = getRoleByIdQueryHandler;
    }

    /**
     * Executes the command to add roles to a user.
     *
     * @param updateRoleCommand The command containing the user ID and role IDs to add
     * @param bindingResult     Not used in this implementation
     * @return ResponseEntity with the updated user
     */
    @Override
    public ResponseEntity<User> execute(UpdateRoleCommand updateRoleCommand, Void bindingResult) {
        Optional<User> userOptional = userRepository.findById(updateRoleCommand.getUserId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(AddRoleCommandHandler.class);
        }
        User user = userOptional.get();
        for (Long roleId : updateRoleCommand.getRoleIds()) {
            Role role = getRoleByIdQueryHandler.execute(roleId).getBody();
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
            }
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
