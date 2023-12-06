package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.model.Appointment;
import com.nergiz.appointmentbookingsystem.model.Notification;
import com.nergiz.appointmentbookingsystem.model.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ReminderService {

    private final AppointmentService appointmentService;

    private final NotificationService notificationService;
    @Autowired
    public  ReminderService(AppointmentService appointmentService, NotificationService notificationService){
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 * * * * *") //every 1 minute at 0th second
    public void checkUpcomingAppointments() {
        log.info("REMINDER SERVICE:");
        log.info(String.valueOf(LocalDateTime.now()));
        try {
            // Get all booked appointments with start time in 15 minutes
            List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointments(15);

            log.info("Found {} upcoming appointments.", upcomingAppointments.size());

            for (Appointment appointment : upcomingAppointments) {
                log.info("Processing appointment with ID: {}", appointment.getId());

                // Create two notifications for both participants
                notificationService.createAndSendNotifications(appointment, NotificationType.REMINDER);
            }

            log.info("Scheduled task completed successfully.");

        } catch (Exception e) {
            log.error("Error occurred during the scheduled task.", e);
        }
    }

}
