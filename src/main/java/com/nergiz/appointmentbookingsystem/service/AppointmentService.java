package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.dto.AppointmentDTO;
import com.nergiz.appointmentbookingsystem.dto.AvailabilitySlotDTO;
import com.nergiz.appointmentbookingsystem.dto.UserDTO;
import com.nergiz.appointmentbookingsystem.model.Appointment;
import com.nergiz.appointmentbookingsystem.model.AppointmentStatus;
import com.nergiz.appointmentbookingsystem.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilitySlotService availabilitySlotService;
    private final UserService userService;

    @Autowired
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AvailabilitySlotService availabilitySlotService,
            UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.availabilitySlotService = availabilitySlotService;
        this.userService = userService;
    }

    @Transactional
    public AppointmentDTO bookAppointment(Long slotId, Long bookerUserId) {
        // Retrieve availability slot DTO
        AvailabilitySlotDTO availabilitySlotDTO = availabilitySlotService.setAvailabilitySlotAvailability(slotId, false);

        // Retrieve booker user DTO
        UserDTO bookerUserDTO = userService.getUserById(bookerUserId);

        // Create an AppointmentDTO
        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .slotDTO(availabilitySlotDTO)
                .bookerUser(bookerUserDTO)
                .appointmentStatus(AppointmentStatus.BOOKED)
                .build();

        // Save and return the appointment
        return saveAppointment(appointmentDTO);
    }

    private AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) {
        Appointment savedAppointment = appointmentRepository.save(convertToEntity(appointmentDTO));
        return convertToDTO(savedAppointment);
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, Long userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));

        // Check if the user is the provider or booker of the appointment
        if (!isParticipant(userId, appointment)) {
            throw new RuntimeException("Unauthorized to cancel appointment with id: " + appointmentId);
        }

        // Update the appointment status to CANCELLED
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

        availabilitySlotService.setAvailabilitySlotAvailability(appointment.getAvailabilitySlot().getId(), true);

        // Save the updated appointment
        appointmentRepository.save(appointment);
    }

    private boolean isParticipant(Long userId, Appointment appointment) {
        return userId.equals(appointment.getAvailabilitySlot().getUser().getId()) ||
                userId.equals(appointment.getBookerUser().getId());
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .slotDTO(availabilitySlotService.convertToDTO(appointment.getAvailabilitySlot()))
                .bookerUser(userService.convertToDTO(appointment.getBookerUser()))
                .appointmentStatus(appointment.getAppointmentStatus())
                .build();
    }

    private Appointment convertToEntity(AppointmentDTO appointmentDTO) {
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .availabilitySlot(availabilitySlotService.convertToEntity(appointmentDTO.getSlotDTO()))
                .bookerUser(userService.convertToEntity(appointmentDTO.getBookerUser()))
                .appointmentStatus(appointmentDTO.getAppointmentStatus())
                .build();
    }
}
