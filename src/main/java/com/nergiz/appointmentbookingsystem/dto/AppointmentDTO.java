package com.nergiz.appointmentbookingsystem.dto;

import com.nergiz.appointmentbookingsystem.model.AppointmentStatus;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AppointmentDTO {
    Long id;
    private UserDTO bookerUser;
    @Builder.Default
    private AppointmentStatus appointmentStatus = AppointmentStatus.BOOKED;
    @Valid
    private AvailabilitySlotDTO availabilitySlotDTO;
    private List<NotificationDTO> notificationDTOList;
}
