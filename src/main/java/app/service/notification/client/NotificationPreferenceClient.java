package app.service.notification.client;

import app.model.dto.notification.NotificationPreferenceRequest;
import app.model.dto.notification.NotificationPreferenceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "notification-preference-client",
        url = "${notification-svc-base-url}/preferences"
)
public interface NotificationPreferenceClient {

    String X_API_KEY = "X-API-Key";

    @PostMapping
    ResponseEntity<NotificationPreferenceResponse> upsertPreference(
            @RequestBody NotificationPreferenceRequest request,
            @RequestHeader(X_API_KEY) String xApiKey
    );

    @PostMapping()
    ResponseEntity<Void> upsertNotificationPreference(
            @RequestBody NotificationPreferenceRequest notificationPreference,
            @RequestHeader(X_API_KEY) String xApiKey
    );

    @GetMapping()
    ResponseEntity<NotificationPreferenceResponse> getUserPreference(
            @RequestParam(name = "userId") String userId,
            @RequestHeader(X_API_KEY) String xApiKey
    );

    @PutMapping()
    ResponseEntity<Void> updateNotificationPreference(
            @RequestParam("userId") String userId,
            @RequestParam("enabled") boolean enabled,
            @RequestHeader(X_API_KEY) String xApiKey
    );
}
