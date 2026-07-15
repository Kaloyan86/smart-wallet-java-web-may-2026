package app.exception.transaction;

import app.exception.ApplicationException;

import java.util.UUID;

/**
 * Exception thrown when a transaction is not found.
 */
public class TransactionNotFoundException extends ApplicationException {

    public TransactionNotFoundException(UUID transactionId) {
        super(
            "Transaction not found with id: " + transactionId,
            "404",
            "Transaction Not Found"
        );
    }
}

