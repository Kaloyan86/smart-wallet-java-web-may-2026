package app.web.notification;

import app.model.dto.notification.NotificationPreferenceResponse;
import app.model.dto.notification.NotificationResponse;
import app.model.dto.user.UserDto;
import app.service.notification.NotificationPreferenceService;
import app.service.notification.NotificationService;
import app.service.user.AuthenticationUserDetails;
import app.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;
    private final NotificationPreferenceService notificationPreferenceService;

    public NotificationController(UserService userService, NotificationService notificationService, NotificationPreferenceService notificationPreferenceService) {
        this.userService = userService;
        this.notificationService = notificationService;
        this.notificationPreferenceService = notificationPreferenceService;
    }

    @GetMapping
    public ModelAndView getNotifications(@AuthenticationPrincipal AuthenticationUserDetails principal) {

        String userId = principal.getId().toString();
        UserDto user = userService.getById(principal.getId());
        NotificationPreferenceResponse notificationPreference = notificationPreferenceService.getNotificationPreference(userId);
        List<NotificationResponse> notificationHistory = notificationService.getNotificationsHistory(userId).getBody();

        //null check
        notificationHistory = notificationHistory == null ? List.of() : notificationHistory;

        long succeededNotificationsNumber = notificationHistory
                .stream()
                .filter(notification -> notification
                        .getStatus()
                        .name().equals("SUCCEEDED")).count();
        long failedNotificationsNumber = notificationHistory
                .stream()
                .filter(notification -> notification
                        .getStatus()
                        .name().equals("FAILED")).count();

        notificationHistory = notificationHistory.stream().limit(5).toList();

        ModelAndView modelAndView = new ModelAndView("notifications");
        modelAndView.addObject("user", user);
        modelAndView.addObject("notificationPreference", notificationPreference);
        modelAndView.addObject("succeededNotificationsNumber", succeededNotificationsNumber);
        modelAndView.addObject("failedNotificationsNumber", failedNotificationsNumber);
        modelAndView.addObject("notificationHistory", notificationHistory);
        return modelAndView;
    }

    @PutMapping
    public ModelAndView retryFailedNotifications(@AuthenticationPrincipal AuthenticationUserDetails principal) {
        notificationService.retryFailedNotifications(principal.getId().toString());
        return new ModelAndView("redirect:/notifications");
    }

    @DeleteMapping
    public String deleteNotificationHistory(@AuthenticationPrincipal AuthenticationUserDetails principal) {

        notificationService.clearHistory( principal.getId().toString());

        return "redirect:/notifications";
    }

    @PutMapping("/user-preference")
    public String updateUserPreference(
            @RequestParam(name = "enabled") boolean enabled,
            @AuthenticationPrincipal AuthenticationUserDetails principal) {

        notificationPreferenceService.updateNotificationPreference(principal.getId().toString(), enabled);

        return "redirect:/notifications";
    }
}













