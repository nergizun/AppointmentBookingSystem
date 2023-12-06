package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.dto.AppointmentDTO;
import com.nergiz.appointmentbookingsystem.dto.NotificationDTO;
import com.nergiz.appointmentbookingsystem.model.Appointment;
import com.nergiz.appointmentbookingsystem.model.AppointmentStatus;
import com.nergiz.appointmentbookingsystem.model.Notification;
import com.nergiz.appointmentbookingsystem.model.NotificationType;
import com.nergiz.appointmentbookingsystem.repository.AppointmentRepository;
import com.nergiz.appointmentbookingsystem.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    @Transactional
    public void createAndSendNotifications(Appointment appointment, NotificationType type) {
        List<Notification> notifications = new ArrayList<>();

        // Create notification for the booker user
        Notification bookerNotification = Notification.builder()
                .type(type)
                .recipientId(appointment.getBookerUser().getId())
                .recipientEmail(appointment.getBookerUser().getEmail())
                .subject(type.getDefaultSubject() + appointment.getId())
                .message(appointment.toString())
                .bookedAppointment(appointment)
                .build();

        notifications.add(bookerNotification);

        // Create notification for the provider user
        Notification providerNotification = Notification.builder()
                .type(type)
                .recipientId(appointment.getAvailabilitySlot().getUser().getId())
                .recipientEmail(appointment.getAvailabilitySlot().getUser().getEmail())
                .subject(type.getDefaultSubject() + appointment.getId() )
                .message(appointment.toString())
                .bookedAppointment(appointment)
                .build();

        notifications.add(providerNotification);

        // Add notifications to the appointment's list
        appointment.getNotifications().addAll(notifications);
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED_REMINDED);
        appointmentRepository.save(appointment);
        sendEmails(notifications);
        notificationRepository.saveAll(notifications);

    }
    private void sendEmails(List<Notification> notifications) {
        for (Notification notification : notifications) {
            log.info("Sending email to {}: Subject - {}, Message - {}",
                    notification.getRecipientEmail(),
                    notification.getSubject(),
                    notification.getMessage());

            // Update notification sent status and sent time
            notification.setSent(true);
            notification.setSentTime(LocalDateTime.now());
        }
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
    public NotificationDTO convertToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .sent(notification.isSent())
                .sentTime(notification.getSentTime())
                .recipientEmail(notification.getRecipientEmail())
                .recipientId(notification.getRecipientId())
                .appointmentId(notification.getBookedAppointment().getId())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .build();
    }
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}

