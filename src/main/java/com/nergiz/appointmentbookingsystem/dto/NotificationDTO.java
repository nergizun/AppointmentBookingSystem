package com.nergiz.appointmentbookingsystem.dto;

import com.nergiz.appointmentbookingsystem.model.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private  Long id;
    private NotificationType type;
    private String recipientEmail;
    private Long recipientId;
    private String subject;
    private String message;
    @Builder.Default
    private boolean sent = false;
    private LocalDateTime sentTime;
    private Long appointmentId;
}
