package com.nergiz.appointmentbookingsystem.event;

import com.nergiz.appointmentbookingsystem.model.Appointment;
import com.nergiz.appointmentbookingsystem.model.NotificationType;
import com.nergiz.appointmentbookingsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @Autowired
    private NotificationService notificationService;

    @Async
    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        NotificationType notificationType = event.getNotificationType();
        Appointment appointment = (Appointment) event.getSource();
        notificationService.createAndSendNotifications(appointment, notificationType);

    }
}
