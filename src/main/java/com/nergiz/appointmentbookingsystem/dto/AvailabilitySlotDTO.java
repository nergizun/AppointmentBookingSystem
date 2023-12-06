package com.nergiz.appointmentbookingsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AvailabilitySlotDTO {

    private Long id;

    @NotNull(message = "Start time cannot be null")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @Builder.Default
    private boolean available = true;

    @Valid
    private UserDTO userDTO;

    @AssertTrue(message = "Start time must be earlier than End time")
    public boolean isStartTimeBeforeEndTime() {
        return startTime.isBefore(endTime);
    }
}
