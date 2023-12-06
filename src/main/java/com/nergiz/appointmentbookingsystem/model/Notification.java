package com.nergiz.appointmentbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_type", columnDefinition = "SMALLINT check (notification_type between 0 and 2)")
    private NotificationType type;

    private String recipientEmail;

    private Long recipientId;

    private String subject;

    @Column(length = 511)
    private String message;

    @Builder.Default
    private boolean sent = false;

    private LocalDateTime sentTime;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment bookedAppointment;

}

