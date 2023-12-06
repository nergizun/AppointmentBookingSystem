package com.nergiz.appointmentbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @OneToMany(mappedBy = "bookedAppointment", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endTime;

    //@Column(name = "appointment_status", columnDefinition = "SMALLINT check (appointment_status between 0 and 2)")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus = AppointmentStatus.BOOKED;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", availabilitySlot=" + availabilitySlot +
                ", bookerUser=" + bookerUser +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", appointmentStatus=" + appointmentStatus +
                '}';
    }
}
