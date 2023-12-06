package com.nergiz.appointmentbookingsystem.event;

import com.nergiz.appointmentbookingsystem.model.NotificationType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent extends ApplicationEvent {

    private final NotificationType notificationType;

    public NotificationEvent(Object source, NotificationType notificationType) {
        super(source);
        this.notificationType = notificationType;
    }

}
