package app.service.notification;

import app.exception.notification.NotificationApiException;
import app.model.dto.notification.NotificationResponse;
import app.service.notification.client.NotificationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    public final NotificationClient notificationClient;

    @Value("${notification.service.api-key}")
    private String apiKey;

    /**
     * Fetches notification history and validates HTTP response status.
     * Returns empty list if 2xx with no body, throws NotificationApiException on non-2xx.
     */
    public List<NotificationResponse> getNotificationsHistory(String userId) {
        try {
            ResponseEntity<List<NotificationResponse>> response = notificationClient.getHistory(userId, apiKey);

            if (!response.getStatusCode().is2xxSuccessful()) {
                String msg = "Notification API returned status " + response.getStatusCodeValue();
                log.error("{} for userId={}", msg, userId);
                throw new NotificationApiException(response.getStatusCodeValue(), msg);
            }

            if (!response.hasBody() || response.getBody() == null) {
                log.warn("Notification API returned empty body for userId={}", userId);
                return Collections.emptyList();
            }

            return response.getBody();

        } catch (NotificationApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error calling Notification API for userId={}", userId, ex);
            throw new NotificationApiException(500, "Error communicating with notification service");
        }
    }

    /**
     * Retries failed notifications, validates response status and throws on failures.
     */
    public void retryFailedNotifications(String userId) {
        try {
            ResponseEntity<Void> response = notificationClient.retryFailedNotification(userId, apiKey);

            if (!response.getStatusCode().is2xxSuccessful()) {
                String msg = "Notification API retry returned status " + response.getStatusCodeValue();
                log.error("{} for userId={}", msg, userId);
                throw new NotificationApiException(response.getStatusCodeValue(), msg);
            }

        } catch (NotificationApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error retrying notifications for userId={}", userId, ex);
            throw new NotificationApiException(500, "Error communicating with notification service");
        }
    }
}
