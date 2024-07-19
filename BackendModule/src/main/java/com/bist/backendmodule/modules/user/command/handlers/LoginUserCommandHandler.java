package com.bist.backendmodule.modules.user.command.handlers;

import com.bist.backendmodule.exceptions.InvalidCredentialsException;
import com.bist.backendmodule.helpers.JwtUtil;
import com.bist.backendmodule.modules.Command;
import com.bist.backendmodule.modules.user.models.User;
import com.bist.backendmodule.modules.user.query.handlers.GetUserByUsernameQueryHandler;
import com.bist.backendmodule.security.models.LoginRequest;
import com.bist.backendmodule.security.models.TokenRequest;
import com.bist.backendmodule.security.models.TokenResponse;
import com.bist.backendmodule.services.RedisTokenService;
import com.bist.backendmodule.validations.LoginRequestValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * Command handler for handling user login and generating authentication tokens.
 */
@Service
public class LoginUserCommandHandler implements Command<LoginRequest, BindingResult, TokenResponse> {
    private final GetUserByUsernameQueryHandler getUserByUsernameQueryHandler;
    private final LoginRequestValidationService loginRequestValidationService;
    private final AuthenticationManager authenticationManager;
    private final RedisTokenService redisTokenService;

    /**
     * Constructs a new LoginUserCommandHandler with the specified dependencies.
     *
     * @param getUserByUsernameQueryHandler The handler to get the user by username
     * @param loginRequestValidationService The service to validate the login request
     * @param authenticationManager         The authentication manager to authenticate the user
     * @param redisTokenService             The service to handle token storage in Redis
     */
    public LoginUserCommandHandler(GetUserByUsernameQueryHandler getUserByUsernameQueryHandler,
                                   LoginRequestValidationService loginRequestValidationService,
                                   AuthenticationManager authenticationManager,
                                   RedisTokenService redisTokenService) {
        this.getUserByUsernameQueryHandler = getUserByUsernameQueryHandler;
        this.loginRequestValidationService = loginRequestValidationService;
        this.authenticationManager = authenticationManager;
        this.redisTokenService = redisTokenService;
    }

    /**
     * Executes the command to handle user login and generate an authentication token.
     *
     * @param loginRequest  The login request containing username and password
     * @param bindingResult The binding result for validation errors
     * @return ResponseEntity containing the generated TokenResponse
     * @throws InvalidCredentialsException if the username or password is invalid
     */
    @Override
    public ResponseEntity<TokenResponse> execute(LoginRequest loginRequest, BindingResult bindingResult) {
        // Validate login request
        loginRequestValidationService.validateLoginRequest(loginRequest, bindingResult, LoginUserCommandHandler.class);

        // Check the accuracy and validity of the username and password
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exception) {
            throw new InvalidCredentialsException(exception, LoginUserCommandHandler.class);
        }

        // Get corresponding user
        final User user = getUserByUsernameQueryHandler.execute(loginRequest.getUsername()).getBody();

        // Create token request according to the corresponding user
        final TokenRequest tokenRequest = new TokenRequest(Objects.requireNonNull(user));

        // Existing token valid bench - Check if existing token is valid
        String existingToken = redisTokenService.getTokenByUsername(user.getUsername());
        if (existingToken != null && !JwtUtil.isTokenExpired(existingToken)) {
            final TokenResponse existingTokenResponse = new TokenResponse(user.getUsername(), existingToken);
            return ResponseEntity.ok().body(existingTokenResponse);
        }

        // No valid token bench
        final TokenResponse tokenResponse = JwtUtil.generateToken(tokenRequest);

        // Save token to Redis
        redisTokenService.saveToken(tokenResponse);

        return ResponseEntity.ok().body(tokenResponse);
    }
}
