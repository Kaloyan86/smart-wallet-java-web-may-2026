package app.web.notification;

import app.model.dto.notification.NotificationResponse;
import app.service.notification.NotificationService;
import app.service.user.AuthenticationUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping
    public ModelAndView getNotifications(@AuthenticationPrincipal AuthenticationUserDetails principal) {
        List<NotificationResponse> notificationsHistory = notificationService
                .getNotificationsHistory(principal.getId().toString())
                .stream()
                .limit(5)
                .toList();

        ModelAndView modelAndView = new ModelAndView("notifications");
        modelAndView.addObject("notificationsHistory", notificationsHistory);
        return modelAndView;
    }

    @PutMapping
    public ModelAndView retryFailedNotifications(@AuthenticationPrincipal AuthenticationUserDetails principal) {
        notificationService.retryFailedNotifications(principal.getId().toString());
        return new ModelAndView("redirect:/notifications");
    }
}













