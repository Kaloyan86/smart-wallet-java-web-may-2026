package app.service.wallet;

import app.model.entity.user.User;
import app.model.entity.wallet.Wallet;
import app.model.entity.wallet.WalletStatus;
import app.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Service
public class WalletService {

    WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createDefaultWallet(User user) {

        Wallet wallet = Wallet.builder()
                .owner(user)
                .currency(Currency.getInstance("EUR"))
                .balance(BigDecimal.valueOf(20.00))
                .status(WalletStatus.ACTIVE)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        walletRepository.save(wallet);

        return wallet;
    }

//    public void createNewWallet(User user) {
//
//        Wallet wallet = Wallet.builder()
//                .owner(user)
//                .currency(Currency.getInstance("EUR"))
//                .balance(BigDecimal.valueOf(0.00))
//                .status(WalletStatus.ACTIVE)
//                .createdOn(LocalDateTime.now())
//                .updatedOn(LocalDateTime.now())
//                .build();
//
//        walletRepository.save(wallet);
//    }
}
