package com.nergiz.appointmentbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AvailabilitySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_ user;

    @NotNull(message = "Start time cannot be null")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean available;

    @Builder
    public AvailabilitySlot(User_ user, LocalDateTime startTime, LocalDateTime endTime, boolean available) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    @Override
    public String toString() {
        return "AvailabilitySlot{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", available=" + available +
                // Other fields excluding references to User_
                '}';
    }
}
