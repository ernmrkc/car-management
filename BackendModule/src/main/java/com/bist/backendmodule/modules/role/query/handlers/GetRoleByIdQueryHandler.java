package com.bist.backendmodule.modules.role.query.handlers;

import com.bist.backendmodule.exceptions.RoleNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a role by its ID.
 */
@Service
public class GetRoleByIdQueryHandler implements Query<Long, Role> {
    private final RoleRepository roleRepository;

    /**
     * Constructs a new GetRoleByIdQueryHandler with the specified role repository.
     *
     * @param roleRepository Role repository for accessing role data
     */
    public GetRoleByIdQueryHandler(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Executes the query to retrieve a role by its ID.
     * Fetches the role from the role repository using the provided ID.
     *
     * @param id The ID of the role to retrieve
     * @return ResponseEntity with the role data
     * @throws RoleNotFoundException if the role with the specified ID is not found
     */
    @Override
    public ResponseEntity<Role> execute(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isEmpty()) {
            throw new RoleNotFoundException(GetRoleByIdQueryHandler.class);
        }
        Role role = roleOptional.get();
        return ResponseEntity.ok().body(role);
    }
}
