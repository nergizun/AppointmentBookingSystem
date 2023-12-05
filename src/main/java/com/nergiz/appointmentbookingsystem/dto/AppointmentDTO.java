package com.nergiz.appointmentbookingsystem.dto;

import com.nergiz.appointmentbookingsystem.model.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AppointmentDTO {
    Long id;
    private AvailabilitySlotDTO slotDTO;
    private UserDTO bookerUser;
    @Builder.Default
    private AppointmentStatus appointmentStatus = AppointmentStatus.BOOKED;
    private List<NotificationDTO> notificationDTOList;
}