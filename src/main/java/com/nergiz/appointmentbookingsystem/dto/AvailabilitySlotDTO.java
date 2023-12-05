package com.nergiz.appointmentbookingsystem.dto;

import com.nergiz.appointmentbookingsystem.model.AvailabilitySlot;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AvailabilitySlotDTO {

    private Long id;
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder.Default
    private boolean available = true;

    private Long providerUserId;

}
