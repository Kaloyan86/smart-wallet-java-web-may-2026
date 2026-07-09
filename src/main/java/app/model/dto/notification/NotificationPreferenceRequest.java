package app.model.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPreferenceRequest {

    private String userId;
    private boolean notificationEnabled;
    private String contactInfo;
}
