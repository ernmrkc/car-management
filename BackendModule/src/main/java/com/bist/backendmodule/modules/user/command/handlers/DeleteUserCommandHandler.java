package com.bist.backendmodule.modules.user.command.handlers;

import com.bist.backendmodule.exceptions.UserNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.user.UserRepository;
import com.bist.backendmodule.modules.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for deleting a user by username.
 */
@Service
public class DeleteUserCommandHandler implements Command<String, Void, Void> {
    private final UserRepository userRepository;

    /**
     * Constructs a new DeleteUserCommandHandler with the specified user repository.
     *
     * @param userRepository The repository for user entities
     */
    public DeleteUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the command to delete a user by username.
     *
     * @param username      The username of the user to be deleted
     * @param bindingResult The binding result for validation errors (not used in this implementation)
     * @return ResponseEntity with no content
     * @throws UserNotFoundException if the user with the specified username is not found
     */
    @Override
    public ResponseEntity<Void> execute(String username, Void bindingResult) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(DeleteUserCommandHandler.class);
        }
        User user = userOptional.get();
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
