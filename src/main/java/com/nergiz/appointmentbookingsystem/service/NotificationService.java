package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.model.Notification;
import com.nergiz.appointmentbookingsystem.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    public Notification createNotification(Notification notification) {
        // Perform any additional validation or business logic before saving
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long notificationId, Notification updatedNotification) {
        Notification existingNotification = notificationRepository.findById(notificationId).orElse(null);
        if (existingNotification != null) {
            // Update fields based on your requirements
            existingNotification.setSent(updatedNotification.isSent());
            // Update other fields as needed
            return notificationRepository.save(existingNotification);
        }
        return null;
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}

