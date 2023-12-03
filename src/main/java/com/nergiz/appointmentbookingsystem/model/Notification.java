package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private NotificationType type;

    private String recipientEmail;

    private String subject;

    private String message;

    private boolean sent;

    private LocalDateTime sentTime;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment bookedAppointment;

}

