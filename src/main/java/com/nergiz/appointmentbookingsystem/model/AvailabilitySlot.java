package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class AvailabilitySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_ user;

    private LocalDateTime startTime;
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
}
