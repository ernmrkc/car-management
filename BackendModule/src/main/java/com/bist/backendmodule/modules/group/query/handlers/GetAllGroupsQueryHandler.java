package com.bist.backendmodule.modules.group.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.group.GroupRepository;
import com.bist.backendmodule.modules.group.models.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Query handler for retrieving all groups.
 */
@Service
public class GetAllGroupsQueryHandler implements Query<Void, List<Group>> {
    private final GroupRepository groupRepository;

    /**
     * Constructs a new GetAllGroupsQueryHandler with the specified repository.
     *
     * @param groupRepository The repository for accessing group data
     */
    public GetAllGroupsQueryHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Executes the query to retrieve all groups.
     *
     * @param input Not used in this implementation, can be null
     * @return A ResponseEntity containing a list of all groups
     */
    @Override
    public ResponseEntity<List<Group>> execute(Void input) {
        List<Group> groupList = groupRepository.findAll();
        return ResponseEntity.ok().body(groupList);
    }
}
