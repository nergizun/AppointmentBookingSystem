package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AvailabilitySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilitySlotId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean available;

    // getters and setters
}
