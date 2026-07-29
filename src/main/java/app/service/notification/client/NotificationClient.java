package app.service.notification.client;

import app.model.dto.notification.NotificationPreferenceRequest;
import app.model.dto.notification.NotificationPreferenceResponse;
import app.model.dto.notification.NotificationRequest;
import app.model.dto.notification.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "notification-client",
        url = "${notification-svc-base-url}"
)
public interface NotificationClient {

    String X_API_KEY = "X-API-Key";

    @GetMapping()
    ResponseEntity<List<NotificationResponse>> getHistory(
            @RequestParam("userId") String userId,
            @RequestHeader(X_API_KEY) String xApiKey);


    @PostMapping
    ResponseEntity<NotificationResponse> sendNotification(
        @RequestBody NotificationRequest notificationRequest,
        @RequestHeader(X_API_KEY) String xApiKey
    );

    @PutMapping
    ResponseEntity<Void> retryFailedNotification(
            @RequestParam String userId,
            @RequestHeader(X_API_KEY) String xApiKey
    );

    @DeleteMapping
    ResponseEntity<Void> deleteAll(
            @RequestParam String userId,
            @RequestHeader(X_API_KEY) String xApiKey
    );
}

