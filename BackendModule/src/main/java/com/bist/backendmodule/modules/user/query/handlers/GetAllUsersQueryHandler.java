package com.bist.backendmodule.modules.user.query.handlers;

import com.bist.backendmodule.modules.Query;
import com.bist.backendmodule.modules.user.UserRepository;
import com.bist.backendmodule.modules.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Query handler for retrieving all users.
 * This class is responsible for handling the query to get a list of all users from the repository.
 */
@Service
public class GetAllUsersQueryHandler implements Query<Void, List<User>> {
    private final UserRepository userRepository;

    /**
     * Constructs a new GetAllUsersQueryHandler with the specified UserRepository.
     *
     * @param userRepository The repository used to access user data.
     */
    public GetAllUsersQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the query to retrieve all users.
     *
     * @param input The input for the query, which is not used in this case.
     * @return A ResponseEntity containing the list of all users.
     */
    @Override
    public ResponseEntity<List<User>> execute(Void input) {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(userList);
    }
}
