package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private AvailabilitySlot slot;

    @ManyToOne
    @JoinColumn(name = "booker_user_id")
    private User bookerUser;

    @OneToMany(mappedBy = "bookedAppointment", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private AppointmentStatus appointmentStatus;

    // getters and setters
}
