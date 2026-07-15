package app.exception.notification;

/**
 * Exception thrown when a notification service API call fails.
 * Used for handling HTTP errors from the external notification service.
 */
public class NotificationApiException extends NotificationException {

    private final int statusCode;

    public NotificationApiException(int statusCode, String message) {
        super(
            message,
            String.valueOf(statusCode),
            "Notification Service Error"
        );
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    public boolean isServerError() {
        return statusCode >= 500;
    }
}

