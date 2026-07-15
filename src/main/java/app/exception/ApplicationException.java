package app.exception;

import lombok.Getter;

/**
 * Base exception class for all application-specific exceptions.
 * Extends RuntimeException for unchecked exception handling.
 */
@Getter
public abstract class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final String errorTitle;

    public ApplicationException(String message, String errorCode, String errorTitle) {
        super(message);
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
    }

}

