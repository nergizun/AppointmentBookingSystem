package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.dto.AppointmentDTO;
import com.nergiz.appointmentbookingsystem.dto.NotificationDTO;
import com.nergiz.appointmentbookingsystem.model.Appointment;
import com.nergiz.appointmentbookingsystem.model.AppointmentStatus;
import com.nergiz.appointmentbookingsystem.model.AvailabilitySlot;
import com.nergiz.appointmentbookingsystem.model.User_;
import com.nergiz.appointmentbookingsystem.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilitySlotService availabilitySlotService;
    private final UserService userService;

    private final NotificationService notificationService;

    @Autowired
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AvailabilitySlotService availabilitySlotService,
            UserService userService,
            NotificationService notificationService) {
        this.appointmentRepository = appointmentRepository;
        this.availabilitySlotService = availabilitySlotService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    // In AppointmentService:

    public AppointmentDTO getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        return convertToDTO(appointment);
    }

    public List<AppointmentDTO> getUserAppointments(Long userId) {
        userService.getUser(userId);
        List<Appointment> userAppointments = appointmentRepository.findByBookerUserIdOrSlotUserId(userId);
        return userAppointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        return allAppointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    @Transactional
    public AppointmentDTO bookAppointment(Long slotId, Long bookerUserId) {
        log.info("BOOK APPOINTMENT");
        // Retrieve availability slot DTO
        AvailabilitySlot availabilitySlot = availabilitySlotService.setAvailabilitySlotAvailability(slotId, false);

        // Retrieve booker user DTO
        User_ bookerUser = userService.getUser(bookerUserId);

        // Create an AppointmentDTO
        Appointment appointment = Appointment.builder()
                .bookerUser(bookerUser)
                .appointmentStatus(AppointmentStatus.BOOKED)
                .startTime(availabilitySlot.getStartTime())
                .endTime(availabilitySlot.getEndTime())
                .availabilitySlot(availabilitySlot)
                .build();

        // Save and return the appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);
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

    public List<Appointment> getUpcomingAppointments(int minutes) {
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(minutes);

        return appointmentRepository.findByStartTimeBeforeAndAppointmentStatus(
                endTime,
                AppointmentStatus.BOOKED
        );
    }

    private boolean isParticipant(Long userId, Appointment appointment) {
        return userId.equals(appointment.getAvailabilitySlot().getUser().getId()) ||
                userId.equals(appointment.getBookerUser().getId());
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        List<NotificationDTO> notificationDTOList = Collections.emptyList();

        if (appointment.getNotifications() != null) {
            notificationDTOList = appointment.getNotifications().stream()
                    .map(notificationService::convertToDTO)
                    .collect(Collectors.toList());
        }

        return AppointmentDTO.builder()
                .id(appointment.getId())
                .availabilitySlotDTO(availabilitySlotService.convertToDTO(appointment.getAvailabilitySlot()))
                .bookerUser(userService.convertToDTO(appointment.getBookerUser()))
                .notificationDTOList(notificationDTOList)
                .appointmentStatus(appointment.getAppointmentStatus())
                .build();
    }


    private Appointment convertToEntity(AppointmentDTO appointmentDTO) {
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .availabilitySlot(availabilitySlotService.convertToEntity(appointmentDTO.getAvailabilitySlotDTO()))
                .bookerUser(userService.getUser(appointmentDTO.getBookerUser().getId()))
                .appointmentStatus(appointmentDTO.getAppointmentStatus())
                .build();
    }
}
