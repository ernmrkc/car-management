package com.bist.backendmodule.modules.group.command.handlers;

import com.bist.backendmodule.exceptions.GroupNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.group.GroupRepository;
import com.bist.backendmodule.modules.group.models.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for deleting a group by name.
 */
@Service
public class DeleteGroupCommandHandler implements Command<String, Void, Void> {
    private final GroupRepository groupRepository;

    /**
     * Constructs a new DeleteGroupCommandHandler with the specified repository.
     *
     * @param groupRepository The repository for accessing group data
     */
    public DeleteGroupCommandHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Executes the command to delete a group by its name.
     * Checks if the group exists and deletes it if found.
     *
     * @param name          The name of the group to be deleted
     * @param bindingResult Not used in this implementation, can be null
     * @return A ResponseEntity with no content
     * @throws GroupNotFoundException if no group with the specified name is found
     */
    @Override
    public ResponseEntity<Void> execute(String name, Void bindingResult) {
        Optional<Group> groupOptional = groupRepository.findByName(name);
        if (groupOptional.isEmpty()) {
            throw new GroupNotFoundException(DeleteGroupCommandHandler.class);
        }
        Group group = groupOptional.get();
        groupRepository.delete(group);
        return ResponseEntity.ok().build();
    }
}
