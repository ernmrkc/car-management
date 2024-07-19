package com.bist.backendmodule.modules.user.query.handlers;

import com.bist.backendmodule.exceptions.UserNotFoundException;
import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.user.UserRepository;
import com.bist.backendmodule.modules.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query handler for retrieving a user by their username.
 * This class is responsible for handling the query to get a user from the repository by their username.
 */
@Service
public class GetUserByUsernameQueryHandler implements Query<String, User> {
    private final UserRepository userRepository;

    /**
     * Constructs a new GetUserByUsernameQueryHandler with the specified UserRepository.
     *
     * @param userRepository The repository used to access user data.
     */
    public GetUserByUsernameQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the query to retrieve a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return A ResponseEntity containing the user, or throws UserNotFoundException if the user is not found.
     */
    @Override
    public ResponseEntity<User> execute(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(GetUserByUsernameQueryHandler.class);
        }
        User user = userOptional.get();
        return ResponseEntity.ok().body(user);
    }
}
