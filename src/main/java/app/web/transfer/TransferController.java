package app.web.transfer;

import app.model.dto.transaction.TransactionDto;
import app.model.dto.transfer.TransferRequest;
import app.model.dto.user.UserDto;
import app.service.user.UserService;
import app.service.wallet.WalletService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/transfers")
public class TransferController {

    private final UserService userService;
    private final WalletService walletService;

    public TransferController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @GetMapping
    public ModelAndView getTransfersPage(HttpSession session) {

        UserDto user = userService.getById((UUID) session.getAttribute("user_id"));

        ModelAndView modelAndView = new ModelAndView("transfer");
        modelAndView.addObject("user", user);
        modelAndView.addObject("transferRequest", TransferRequest.builder().build());

        return modelAndView;
    }

    @PostMapping
    public ModelAndView initiateTransfer(@Valid TransferRequest transferRequest,
                                         BindingResult bindingResult,
                                         HttpSession session) {

        UserDto user = userService.getById((UUID) session.getAttribute("user_id"));

        if (bindingResult.hasErrors()) {
           ModelAndView modelAndView = new ModelAndView("transfer");
           modelAndView.addObject("transferRequest", transferRequest);
           modelAndView.addObject("user", user);

           return modelAndView;
        }

        TransactionDto transaction =  walletService.transferFunds(user, transferRequest);
        return new ModelAndView("redirect:/transactions/" + transaction.getId());
    }
}
