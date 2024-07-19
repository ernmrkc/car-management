package com.bist.backendmodule.modules.group.query.handlers;

import com.bist.backendmodule.exceptions.GroupNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.group.GroupRepository;
import com.bist.backendmodule.modules.group.models.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a group by its name.
 */
@Service
public class GetGroupByNameQueryHandler implements Query<String, Group> {
    private final GroupRepository groupRepository;

    /**
     * Constructs a new GetGroupByNameQueryHandler with the specified repository.
     *
     * @param groupRepository The repository for accessing group data
     */
    public GetGroupByNameQueryHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Executes the query to retrieve a group by its name.
     *
     * @param name The name of the group to retrieve
     * @return A ResponseEntity containing the group if found, otherwise throws GroupNotFoundException
     * @throws GroupNotFoundException if the group with the specified name is not found
     */
    @Override
    public ResponseEntity<Group> execute(String name) {
        Optional<Group> groupOptional = groupRepository.findByName(name);
        if (groupOptional.isEmpty()) {
            throw new GroupNotFoundException(GetGroupByNameQueryHandler.class);
        }
        Group group = groupOptional.get();
        return ResponseEntity.ok().body(group);
    }
}
