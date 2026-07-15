package app.exception.user;

import app.exception.ApplicationException;

import java.util.UUID;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(UUID userId) {
        super(
            "User with id [%s] does not exist.".formatted(userId),
            "404",
            "User Not Found"
        );
    }
}

