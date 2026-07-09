package app.service.notification;

import app.model.dto.notification.NotificationResponse;
import app.service.notification.client.NotificationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
