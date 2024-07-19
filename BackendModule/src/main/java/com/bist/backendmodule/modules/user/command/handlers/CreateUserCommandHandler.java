package com.bist.backendmodule.modules.user.command.handlers;

import com.bist.backendmodule.exceptions.UserAlreadyExistsException;
import com.bist.backendmodule.helpers.MD5Util;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.group.models.Group;
import com.bist.backendmodule.modules.group.query.handlers.GetGroupByIdQueryHandler;
import com.bist.backendmodule.modules.role.models.Role;
import com.bist.backendmodule.modules.role.query.handlers.GetRoleByIdQueryHandler;
import com.bist.backendmodule.modules.user.UserRepository;
import com.bist.backendmodule.modules.user.models.User;
import com.bist.backendmodule.modules.user.models.UserCreateDTO;
import com.bist.backendmodule.validations.UserCreateDTOValidationService;
import com.bist.backendmodule.validations.UserValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Command handler for creating a new user.
 */
@Service
public class CreateUserCommandHandler implements Command<UserCreateDTO, BindingResult, User> {
    private final UserRepository userRepository;
    private final UserCreateDTOValidationService userCreateDTOValidationService;
    private final UserValidationService userValidationService;
    private final GetRoleByIdQueryHandler getRoleByIdQueryHandler;
    private final GetGroupByIdQueryHandler getGroupByIdQueryHandler;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new CreateUserCommandHandler with the specified dependencies.
     *
     * @param userRepository                 The repository for user entities
     * @param userCreateDTOValidationService The service for validating user creation DTO
     * @param userValidationService          The service for validating user entities
     * @param getRoleByIdQueryHandler        The query handler for retrieving roles by ID
     * @param getGroupByIdQueryHandler       The query handler for retrieving groups by ID
     * @param passwordEncoder                The password encoder for encrypting user passwords
     */
    public CreateUserCommandHandler(UserRepository userRepository,
                                    UserCreateDTOValidationService userCreateDTOValidationService,
                                    UserValidationService userValidationService,
                                    GetRoleByIdQueryHandler getRoleByIdQueryHandler,
                                    GetGroupByIdQueryHandler getGroupByIdQueryHandler,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCreateDTOValidationService = userCreateDTOValidationService;
        this.userValidationService = userValidationService;
        this.getRoleByIdQueryHandler = getRoleByIdQueryHandler;
        this.getGroupByIdQueryHandler = getGroupByIdQueryHandler;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Executes the command to create a new user.
     *
     * @param userCreateDTO The DTO containing the user creation details
     * @param bindingResult The binding result for validation errors
     * @return ResponseEntity with the created user
     */
    @Override
    public ResponseEntity<User> execute(UserCreateDTO userCreateDTO, BindingResult bindingResult) {
        // Validate UserCreateDTO data
        userCreateDTOValidationService.validateUserCreateDTO(userCreateDTO, bindingResult, CreateUserCommandHandler.class);

        // Check if username already exist
        Optional<User> userOptional = userRepository.findByUsername(userCreateDTO.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(CreateUserCommandHandler.class);
        }

        User user = new User(userCreateDTO);

        // Crypt password with BCrypt
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        // Crypt password with MD5
        // user.setPassword(MD5Util.hash(userCreateDTO.getPassword()));

        // Set password expiration date
        user.setPasswordExpirationDate(LocalDateTime.now().plusDays(10));

        // Set roles if applicable
        if (!userCreateDTO.getRoleIds().isEmpty()) {
            List<Role> roles = new ArrayList<>();
            for (Long roleId : userCreateDTO.getRoleIds()) {
                Role role = getRoleByIdQueryHandler.execute(roleId).getBody();
                roles.add(role);
            }
            user.setRoles(roles);
        }

        // Set groups if applicable
        if (!userCreateDTO.getGroupIds().isEmpty()) {
            List<Group> groups = new ArrayList<>();
            for (Long groupId : userCreateDTO.getGroupIds()) {
                Group group = getGroupByIdQueryHandler.execute(groupId).getBody();
                groups.add(group);
            }
            user.setGroups(groups);
        }

        // Validate User data
        userValidationService.validateUser(user, bindingResult, CreateUserCommandHandler.class);

        // Save user to repository
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
