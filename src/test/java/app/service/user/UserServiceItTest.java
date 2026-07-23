package app.service.user;

import app.model.dto.user.UserDto;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.subscription.Subscription;
import app.model.entity.subscription.SubscriptionType;
import app.model.entity.user.User;
import app.model.entity.wallet.Wallet;
import app.repository.subscription.SubscriptionRepository;
import app.repository.user.UserRepository;
import app.repository.wallet.WalletRepository;
import app.service.subscription.SubscriptionService;
import app.service.wallet.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static app.util.user.UserFactory.getUserRegisterRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceItTest {

    @Autowired
    private UserService underTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Test
    public void testRegisterUser_shouldRegisterUser_withDefaultSubscriptionAndWallet() {

        UserRegisterRequest userRegisterRequest = getUserRegisterRequest();

        UserDto registeredUser = underTest.register(userRegisterRequest);

        UUID walletId = registeredUser.getWallets().get(0).getId();
        UUID subscriptionId = registeredUser.getSubscriptions().get(0).getId();

        Wallet wallet = walletRepository.findById(walletId).get();
        Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
        User user = userRepository.findById(registeredUser.getId()).get();

        assertEquals(BigDecimal.valueOf(20.00).setScale(2), wallet.getBalance());
        assertEquals(Currency.getInstance("EUR"), wallet.getCurrency());
        assertEquals(user.getId(), wallet.getOwner().getId());

        assertEquals(SubscriptionType.DEFAULT, subscription.getType());
        assertEquals(user.getId(), subscription.getOwner().getId());

        assertEquals(userRegisterRequest.getUsername(), user.getUsername());
        assertEquals(userRegisterRequest.getCountry(), user.getCountry());
    }
}
