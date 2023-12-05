package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_type", columnDefinition = "SMALLINT check (notification_type between 0 and 2)")
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

