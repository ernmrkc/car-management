package com.bist.backendmodule.modules.group.command.handlers;

import com.bist.backendmodule.exceptions.GroupAlreadyExistsException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.group.GroupRepository;
import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.validations.GroupValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

/**
 * Service for handling the creation of Group entities
 */
@Service
public class CreateGroupCommandHandler implements Command<Group, BindingResult, Group> {
    private final GroupRepository groupRepository;
    private final GroupValidationService groupValidationService;

    /**
     * Constructs a new CreateGroupCommandHandler with the specified repository and validation service.
     *
     * @param groupRepository        The repository for accessing group data
     * @param groupValidationService The service for validating group data
     */
    public CreateGroupCommandHandler(GroupRepository groupRepository,
                                     GroupValidationService groupValidationService) {
        this.groupRepository = groupRepository;
        this.groupValidationService = groupValidationService;
    }

    /**
     * Executes the command to create a new group.
     * Validates the group, checks for existing groups with the same name,
     * and saves the new group to the repository.
     *
     * @param group         The group entity to be created
     * @param bindingResult The binding result for validation errors
     * @return A ResponseEntity containing the created group
     * @throws GroupAlreadyExistsException if a group with the same name already exists
     */
    @Override
    public ResponseEntity<Group> execute(Group group, BindingResult bindingResult) {
        // Validate Group
        groupValidationService.validateGroup(group, bindingResult, CreateGroupCommandHandler.class);

        // Check if group already exists
        Optional<Group> groupOptional = groupRepository.findByName(group.getName());
        if (groupOptional.isPresent()) {
            throw new GroupAlreadyExistsException(CreateGroupCommandHandler.class);
        }

        // Save group to repository
        groupRepository.save(group);

        return ResponseEntity.ok().body(group);
    }
}
