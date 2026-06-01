package app.web.subscription;

import app.model.dto.user.UserDto;
import app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final UserService userService;

    public SubscriptionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getSubscriptionsPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upgrade");

        return modelAndView;
    }

    @GetMapping("/history")
    public ModelAndView getSubscriptionHistoryPage() {

        UserDto user = userService.getById("532abc8e-ed27-439b-9861-94d6fae05005");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("subscription-history");
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
