package app.model.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPreferenceResponse {

    private NotificationType type;
    private boolean enabled;
    private String contactInfo;
}
