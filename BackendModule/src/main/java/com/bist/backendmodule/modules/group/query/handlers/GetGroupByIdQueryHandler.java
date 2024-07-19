package com.bist.backendmodule.modules.group.query.handlers;

import com.bist.backendmodule.exceptions.GroupNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.group.GroupRepository;
import com.bist.backendmodule.modules.group.models.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a group by its ID.
 */
@Service
public class GetGroupByIdQueryHandler implements Query<Long, Group> {
    private final GroupRepository groupRepository;

    /**
     * Constructs a new GetGroupByIdQueryHandler with the specified repository.
     *
     * @param groupRepository The repository for accessing group data
     */
    public GetGroupByIdQueryHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Executes the query to retrieve a group by its ID.
     *
     * @param id The ID of the group to retrieve
     * @return A ResponseEntity containing the group if found, otherwise throws GroupNotFoundException
     * @throws GroupNotFoundException if the group with the specified ID is not found
     */
    @Override
    public ResponseEntity<Group> execute(Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isEmpty()) {
            throw new GroupNotFoundException(GetGroupByIdQueryHandler.class);
        }
        Group group = groupOptional.get();
        return ResponseEntity.ok().body(group);
    }
}
