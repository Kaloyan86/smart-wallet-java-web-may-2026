package app.exception.wallet;

import app.exception.ApplicationException;

public class UnauthorizedWalletAccessException extends ApplicationException {

    public UnauthorizedWalletAccessException() {
        super(
                "You are not authorized to switch the status of this wallet.",
                "403",
                "Unauthorized Wallet Access"
        );
    }

}
