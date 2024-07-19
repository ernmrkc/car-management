package com.bist.backendmodule.modules.role.query.handlers;

import com.bist.backendmodule.exceptions.RoleNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a role by its name.
 */
@Service
public class GetRoleByNameQueryHandler implements Query<String, Role> {
    private final RoleRepository roleRepository;

    /**
     * Constructs a new GetRoleByNameQueryHandler with the specified role repository.
     *
     * @param roleRepository Role repository for accessing role data
     */
    public GetRoleByNameQueryHandler(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Executes the query to retrieve a role by its name.
     * Fetches the role from the role repository using the provided name.
     *
     * @param name The name of the role to retrieve
     * @return ResponseEntity with the role data
     * @throws RoleNotFoundException if the role with the specified name is not found
     */
    @Override
    public ResponseEntity<Role> execute(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isEmpty()){
            throw new RoleNotFoundException(GetRoleByNameQueryHandler.class);
        }
        Role role = roleOptional.get();
        return ResponseEntity.ok().body(role);
    }
}
