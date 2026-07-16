package app.exception.wallet;

import app.exception.ApplicationException;

public class WalletNotFoundException extends ApplicationException {

    public WalletNotFoundException(String walletId) {
        super(
                "Wallet with id " + walletId + "not found!",
                "404",
                "Wallet Not Found"
        );
    }
}
