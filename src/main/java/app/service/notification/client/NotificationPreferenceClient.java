package app.service.notification.client;

import app.model.dto.notification.NotificationPreferenceRequest;
import app.model.dto.notification.NotificationPreferenceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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
}
