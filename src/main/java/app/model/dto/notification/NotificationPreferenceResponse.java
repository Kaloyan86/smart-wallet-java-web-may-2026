package app.model.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPreferenceResponse {

    private NotificationType notificationType;
    private boolean enabled;
    private String contactInfo;
}
