package com.bist.backendmodule.modules.user.command.handlers;

import com.bist.backendmodule.exceptions.UserNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.modules.group.query.handlers.GetGroupByIdQueryHandler;
import com.bist.backendmodule.modules.user.UserRepository;
import com.bist.backendmodule.modules.user.models.UpdateGroupCommand;
import com.bist.backendmodule.modules.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for removing groups from a user.
 * Implements the {@link Command} interface.
 */
@Service
public class RemoveGroupCommandHandler implements Command<UpdateGroupCommand, Void, User> {
    private final UserRepository userRepository;
    private final GetGroupByIdQueryHandler getGroupByIdQueryHandler;

    /**
     * Constructs a new RemoveGroupCommandHandler with the specified dependencies.
     *
     * @param userRepository The repository to manage users
     * @param getGroupByIdQueryHandler The handler to get a group by its ID
     */
    public RemoveGroupCommandHandler(UserRepository userRepository,
                                     GetGroupByIdQueryHandler getGroupByIdQueryHandler) {
        this.userRepository = userRepository;
        this.getGroupByIdQueryHandler = getGroupByIdQueryHandler;
    }

    /**
     * Executes the command to remove groups from a user.
     *
     * @param updateGroupCommand The command containing the user ID and group IDs to remove
     * @param bindingResult The binding result for validation errors (not used in this implementation)
     * @return ResponseEntity containing the updated User
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public ResponseEntity<User> execute(UpdateGroupCommand updateGroupCommand, Void bindingResult) {
        Optional<User> userOptional = userRepository.findById(updateGroupCommand.getUserId());
        if (userOptional.isEmpty()){
            throw new UserNotFoundException(RemoveGroupCommandHandler.class);
        }
        User user = userOptional.get();
        for (Long groupId : updateGroupCommand.getGroupIds()){
            Group group = getGroupByIdQueryHandler.execute(groupId).getBody();
            user.getGroups().remove(group);
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
