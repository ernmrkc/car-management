package com.bist.backendmodule.modules.role.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Query handler for retrieving all roles.
 * Implements the {@link Query} interface to handle fetching all roles from the repository.
 */
@Service
public class GetAllRolesQueryHandler implements Query<Void, List<Role>> {
    private final RoleRepository roleRepository;

    /**
     * Constructs a new GetAllRolesQueryHandler with the specified role repository.
     *
     * @param roleRepository Role repository for accessing role data
     */
    public GetAllRolesQueryHandler(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Executes the query to retrieve all roles.
     * Fetches all roles from the role repository.
     *
     * @param input Not used in this query
     * @return ResponseEntity with the list of all roles
     */
    @Override
    public ResponseEntity<List<Role>> execute(Void input) {
        List<Role> roleList = roleRepository.findAll();
        return ResponseEntity.ok().body(roleList);
    }
}
