package app.web.wallet;

import app.model.dto.user.UserDto;
import app.model.dto.wallet.WalletDto;
import app.repository.wallet.WalletRepository;
import app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/wallets")
public class WalletController {

    private final UserService userService;

    public WalletController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getWallets() {
        UserDto user = userService.getById("532abc8e-ed27-439b-9861-94d6fae05005");
        List<WalletDto> wallets = user.getWallets();

         ModelAndView modelAndView = new ModelAndView();
         modelAndView.setViewName("wallets");
         modelAndView.addObject("wallets", wallets);

         return modelAndView;
    }
}
