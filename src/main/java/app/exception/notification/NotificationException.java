package app.exception.notification;

import app.exception.ApplicationException;

/**
 * Base exception class for notification-related exceptions.
 * Can be extended for specific notification errors.
 */
public class NotificationException extends ApplicationException {

    public NotificationException(String message, String errorCode, String errorTitle) {
        super(message, errorCode, errorTitle);
    }
}

