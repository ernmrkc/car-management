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
 * Command handler for adding groups to a user.
 */
@Service
public class AddGroupCommandHandler implements Command<UpdateGroupCommand, Void, User> {
    private final UserRepository userRepository;
    private final GetGroupByIdQueryHandler getGroupByIdQueryHandler;

    /**
     * Constructs a new AddGroupCommandHandler with the specified repositories and query handlers.
     *
     * @param userRepository           The repository for user entities
     * @param getGroupByIdQueryHandler The query handler for retrieving groups by ID
     */
    public AddGroupCommandHandler(UserRepository userRepository,
                                  GetGroupByIdQueryHandler getGroupByIdQueryHandler) {
        this.userRepository = userRepository;
        this.getGroupByIdQueryHandler = getGroupByIdQueryHandler;
    }

    /**
     * Executes the command to add groups to a user.
     *
     * @param updateGroupCommand The command containing the user ID and group IDs to add
     * @param bindingResult      Not used in this implementation
     * @return ResponseEntity with the updated user
     */
    @Override
    public ResponseEntity<User> execute(UpdateGroupCommand updateGroupCommand, Void bindingResult) {
        Optional<User> userOptional = userRepository.findById(updateGroupCommand.getUserId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(AddGroupCommandHandler.class);
        }
        User user = userOptional.get();
        for (Long groupId : updateGroupCommand.getGroupIds()) {
            Group group = getGroupByIdQueryHandler.execute(groupId).getBody();
            if (!user.getGroups().contains(group)) {
                user.getGroups().add(group);
            }
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
