package com.bist.backendmodule.modules.role.command.handlers;

import com.bist.backendmodule.exceptions.RoleNotFoundException;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.role.RoleRepository;
import com.bist.backendmodule.modules.role.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command handler for deleting an existing role.
 */
@Service
public class DeleteRoleCommandHandler implements Command<String, Void, Void> {
    private final RoleRepository roleRepository;

    /**
     * Constructs a new DeleteRoleCommandHandler with the specified role repository.
     *
     * @param roleRepository Role repository for accessing role data
     */
    public DeleteRoleCommandHandler(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Executes the command to delete a role.
     * Finds the role by name and deletes it if it exists.
     *
     * @param name          The name of the role to be deleted
     * @param bindingResult Not used in this command
     * @return ResponseEntity with no content if the deletion is successful
     * @throws RoleNotFoundException if the role with the specified name is not found
     */
    @Override
    public ResponseEntity<Void> execute(String name, Void bindingResult) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isEmpty()) {
            throw new RoleNotFoundException(DeleteRoleCommandHandler.class);
        }
        Role role = roleOptional.get();
        roleRepository.delete(role);
        return ResponseEntity.ok().build();
    }
}
