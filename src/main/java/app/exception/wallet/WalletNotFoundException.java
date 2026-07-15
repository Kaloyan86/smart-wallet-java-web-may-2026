package app.exception.wallet;

import app.exception.ApplicationException;

import java.util.UUID;

/**
 * Exception thrown when a wallet is not found.
 */
public class WalletNotFoundException extends ApplicationException {

    public WalletNotFoundException(UUID walletId) {
        super(
            "Wallet with id [%s] not found.".formatted(walletId),
            "404",
            "Wallet Not Found"
        );
    }
}

