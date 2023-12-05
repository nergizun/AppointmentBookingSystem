package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.dto.AvailabilitySlotDTO;
import com.nergiz.appointmentbookingsystem.model.AvailabilitySlot;
import com.nergiz.appointmentbookingsystem.model.User_;
import com.nergiz.appointmentbookingsystem.repository.AvailabilitySlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilitySlotService {

    private final AvailabilitySlotRepository availabilitySlotRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AvailabilitySlotService(
            AvailabilitySlotRepository availabilitySlotRepository,
            UserService userService,
            ApplicationEventPublisher eventPublisher) {
        this.availabilitySlotRepository = availabilitySlotRepository;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public AvailabilitySlotDTO addAvailabilitySlot(Long userId, AvailabilitySlotDTO availabilitySlotDTO) {
        User_ user = userService.getUser(userId);
        Long existingSlotId = isNotEncapsulated(userId, availabilitySlotDTO);

        if (existingSlotId<0) {
            AvailabilitySlot availabilitySlot = convertToEntity(availabilitySlotDTO);
            availabilitySlot.setUser(user);

            availabilitySlot = availabilitySlotRepository.save(availabilitySlot);
            return convertToDTO(availabilitySlot);
        } else {
            throw new RuntimeException("New availability slot is encapsulated by an existing slot with id " + existingSlotId);
        }
    }

    private Long isNotEncapsulated(Long userId, AvailabilitySlotDTO newSlotDTO) {
        List<AvailabilitySlot> userAvailabilitySlots = availabilitySlotRepository.findByUserId(userId);

        for (AvailabilitySlot existingSlot : userAvailabilitySlots) {
            if (isEncapsulated(existingSlot, newSlotDTO)) {
                return existingSlot.getId();
            }
        }

        return (long) -1; // Not encapsulated, can add the new slot
    }

    private boolean isEncapsulated(AvailabilitySlot existingSlot, AvailabilitySlotDTO newSlotDTO) {
        LocalDateTime existingStart = existingSlot.getStartTime();
        LocalDateTime existingEnd = existingSlot.getEndTime();
        LocalDateTime newStart = newSlotDTO.getStartTime();
        LocalDateTime newEnd = newSlotDTO.getEndTime();

        // Check for non-overlapping cases
        if (newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd)) {
            return false;
        }

        // If we reach here, there is some overlap
        return true;
    }

    @Transactional
    public void updateAvailabilitySlot(Long userId, Long slotId, AvailabilitySlotDTO updatedSlotDTO) {
        User_ user = userService.getUser(userId);

        AvailabilitySlot availabilitySlot = availabilitySlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Availability slot not found with id: " + slotId));

        // Check if the availability slot belongs to the user
        if (!availabilitySlot.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized to update availability slot for user with id: " + userId);
        }
        availabilitySlot.setStartTime(updatedSlotDTO.getStartTime());
        availabilitySlot.setEndTime(updatedSlotDTO.getEndTime());
        availabilitySlot.setAvailable(updatedSlotDTO.isAvailable());

        availabilitySlotRepository.save(availabilitySlot);
    }

    @Transactional
    public void deleteAvailabilitySlot(Long userId, Long slotId) throws AuthenticationException {
        User_ user = userService.getUser(userId);

        AvailabilitySlot availabilitySlot = availabilitySlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Availability slot not found with id: " + slotId));

        // Check if the availability slot belongs to the user
        if (!availabilitySlot.getUser().equals(user)) {
            throw new AuthenticationException("Unauthorized to delete availability slot for user with id: " + userId);
        }

        availabilitySlotRepository.delete(availabilitySlot);
    }

    @Transactional(readOnly = true)
    public List<AvailabilitySlotDTO> getUserAvailabilitySlots(Long userId) {
        User_ user = userService.getUser(userId);

        List<AvailabilitySlot> userAvailabilitySlots = availabilitySlotRepository.findByUserId(userId);
        return userAvailabilitySlots.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvailabilitySlotDTO> getAllAvailabilitySlots() {
        List<AvailabilitySlot> allAvailabilitySlots = availabilitySlotRepository.findAll();
        return allAvailabilitySlots.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public AvailabilitySlotDTO getAllAvailabilitySlotById(Long slotId) {
        AvailabilitySlot allAvailabilitySlot = availabilitySlotRepository.findById(slotId)
                .orElseThrow();
        return convertToDTO(allAvailabilitySlot);
    }


    public AvailabilitySlotDTO convertToDTO(AvailabilitySlot availabilitySlot) {
        return AvailabilitySlotDTO.builder()
                .id(availabilitySlot.getId())
                .startTime(availabilitySlot.getStartTime())
                .endTime(availabilitySlot.getEndTime())
                .userDTO(userService.convertToDTO(availabilitySlot.getUser()))
                .available(availabilitySlot.isAvailable())
                .build();
    }

    public AvailabilitySlot convertToEntity(AvailabilitySlotDTO availabilitySlotDTO) {

        return AvailabilitySlot.builder()
                .startTime(availabilitySlotDTO.getStartTime())
                .endTime(availabilitySlotDTO.getEndTime())
                .available(availabilitySlotDTO.isAvailable())
                .build();
    }

    public AvailabilitySlot setAvailabilitySlotAvailability(Long slotId, boolean available){
        AvailabilitySlot slot =  availabilitySlotRepository.findById(slotId).orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setAvailable(available);
        availabilitySlotRepository.save(slot);
        return slot;
    }
}
