package app.service.user;

import app.mapper.user.UserMapper;
import app.model.dto.user.UserDto;
import app.model.dto.user.UserRegisterRequest;
import app.model.entity.subscription.Subscription;
import app.model.entity.user.User;
import app.model.entity.wallet.Wallet;
import app.repository.user.UserRepository;
import app.service.subscription.SubscriptionService;
import app.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private SubscriptionService subscriptionService;
    private WalletService walletService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SubscriptionService subscriptionService, WalletService walletService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionService = subscriptionService;
        this.walletService = walletService;
    }

    public UserDto register(UserRegisterRequest userRegisterRequest){
        //   1.	Account Creation: Validate the username to ensure its unique and store the user’s details securely.
        //   You must consider persisting user’s sensitive data in a secure way!

        userRepository.findByUsername(userRegisterRequest.getUsername())
                .ifPresent(user -> {
            //TODO: Create custom exception e.g. UserAlreadyExistsException
            throw new RuntimeException("User with this username already exists!");
        });

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodedPassword);


        User userEntity = UserMapper.toUserEntity(userRegisterRequest);

        //  3.Default Subscription Setup: Assign a free subscription to the user upon registration
        Subscription defaultSubscription = subscriptionService.createDefaultSubscription(userEntity);
        userEntity.setSubscriptions(List.of(defaultSubscription));

        //  2.Default Wallet Creation: Automatically create a wallet for the user
        Wallet defaultWallet = walletService.createDefaultWallet(userEntity);
        userEntity.setWallets(List.of(defaultWallet));

        userRepository.save(userEntity);

        return UserMapper.toUserDto(userEntity);
    }

}
