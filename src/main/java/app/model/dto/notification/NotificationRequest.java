package app.model.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {

    private String userId;
    private String subject;
    private String body;
}
