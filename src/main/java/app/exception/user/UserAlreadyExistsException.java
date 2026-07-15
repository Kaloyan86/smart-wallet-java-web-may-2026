package app.exception.user;

import app.exception.ApplicationException;

/**
 * Exception thrown when attempting to register a user with a username that already exists.
 */
public class UserAlreadyExistsException extends ApplicationException {

    public UserAlreadyExistsException(String username) {
        super(
            "User with this username already exists!",
            "409",
            "User Already Exists"
        );
    }
}

