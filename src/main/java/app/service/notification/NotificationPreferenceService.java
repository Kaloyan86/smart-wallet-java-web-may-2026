package app.service.notification;

import app.model.dto.notification.NotificationPreferenceRequest;
import app.model.dto.notification.NotificationPreferenceResponse;
import app.service.notification.client.NotificationClient;
import app.service.notification.client.NotificationPreferenceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationPreferenceService {

    private final NotificationPreferenceClient notificationPreferenceClient;

    @Value("${notification.service.api-key}")
    private String apiKey;

    public NotificationPreferenceResponse getNotificationPreference(String userId) {

        ResponseEntity<NotificationPreferenceResponse> httpResponse = notificationPreferenceClient.getUserPreference(userId, apiKey);

        if (!httpResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Notification preference for user id [%s] does not exist.".formatted(userId));
        }

        return httpResponse.getBody();
    }

    public void updateNotificationPreference(String userId, boolean enabled) {
        notificationPreferenceClient.updateNotificationPreference(userId, enabled, apiKey);
    }

    public void saveNotificationPreference(String userId, boolean isEmailEnabled, String email) {

        NotificationPreferenceRequest notificationPreference = NotificationPreferenceRequest.builder()
                .userId(userId)
                .contactInfo(email)
                .notificationEnabled(isEmailEnabled)
                .build();

        ResponseEntity<Void> httpResponse = notificationPreferenceClient.upsertNotificationPreference(notificationPreference, apiKey);
    }
}
