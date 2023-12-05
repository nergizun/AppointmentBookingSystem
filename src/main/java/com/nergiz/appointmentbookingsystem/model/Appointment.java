package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private AvailabilitySlot availabilitySlot;

    @ManyToOne
    @JoinColumn(name = "booker_user_id")
    private User_ bookerUser;

    @OneToMany(mappedBy = "bookedAppointment", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //@Column(name = "appointment_status", columnDefinition = "SMALLINT check (appointment_status between 0 and 2)")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus = AppointmentStatus.BOOKED;

    @Builder
    public Appointment(
            Long id,
            AvailabilitySlot availabilitySlot,
            User_ bookerUser,
            List<Notification> notifications,
            LocalDateTime startTime,
            LocalDateTime endTime,
            AppointmentStatus appointmentStatus
    ) {
        this.id = id;
        this.availabilitySlot = availabilitySlot;
        this.bookerUser = bookerUser;
        this.notifications = notifications;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentStatus = appointmentStatus;
    }
}
