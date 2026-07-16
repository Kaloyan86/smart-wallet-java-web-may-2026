package app.exception.transaction;

import app.exception.ApplicationException;

public class TransactionNotFoundException extends ApplicationException {

    public TransactionNotFoundException(String id) {
        super(
                "Transaction with id: %s not found".formatted(id),
                "404",
                "Transaction Not Found"
        );
    }
}
