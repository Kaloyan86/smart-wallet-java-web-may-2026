package app.exception.subscription;

import app.exception.ApplicationException;

/**
 * Base exception class for subscription-related exceptions.
 * Can be extended for specific subscription errors.
 */
public class SubscriptionException extends ApplicationException {

    public SubscriptionException(String message, String errorCode, String errorTitle) {
        super(message, errorCode, errorTitle);
    }
}

