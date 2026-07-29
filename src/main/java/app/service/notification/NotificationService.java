package app.service.notification;

import app.model.dto.notification.NotificationRequest;
import app.model.dto.notification.NotificationResponse;
import app.service.notification.client.NotificationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    public final NotificationClient notificationClient;

    @Value("${notification.service.api-key}")
    private String apiKey;

    public ResponseEntity<List<NotificationResponse>> getNotificationsHistory(String userId) {
        return notificationClient.getHistory(userId, apiKey);
    }

    public ResponseEntity<Void> retryFailedNotifications(String userId) {
        return notificationClient.retryFailedNotification(userId, apiKey);
    }

    public void sendNotification(UUID userId, String emailSubject, String emailBody) {

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(userId.toString())
                .subject(emailSubject)
                .body(emailBody)
                .build();

        ResponseEntity<NotificationResponse> httpResponse;

        httpResponse = notificationClient.sendNotification(notificationRequest, apiKey);

        if (!httpResponse.getStatusCode().is2xxSuccessful()) {
            log.error("[Feign call to notification-svc failed] Can't send email to user with id = [{}]", userId);
        }
    }

    public void clearHistory(String userId) {
        notificationClient.deleteAll(userId.toString(), apiKey);
    }
}
