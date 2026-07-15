package app.exception.wallet;

import app.exception.ApplicationException;

/**
 * Exception thrown when a user attempts unauthorized wallet operations.
 */
public class UnauthorizedWalletAccessException extends ApplicationException {

    public UnauthorizedWalletAccessException() {
        super(
            "You are not authorized to switch the status of this wallet.",
            "403",
            "Unauthorized Access"
        );
    }
}

