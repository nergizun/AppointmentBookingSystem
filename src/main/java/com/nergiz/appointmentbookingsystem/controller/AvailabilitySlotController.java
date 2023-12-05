package com.nergiz.appointmentbookingsystem.controller;

import com.nergiz.appointmentbookingsystem.dto.AvailabilitySlotDTO;
import com.nergiz.appointmentbookingsystem.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nergiz.appointmentbookingsystem.service.AvailabilitySlotService;


import javax.security.sasl.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/availability-slots")
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    @Autowired
    public AvailabilitySlotController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AvailabilitySlotDTO>> getAllAvailabilitySlots() {
        List<AvailabilitySlotDTO> availabilitySlots = availabilitySlotService.getAllAvailabilitySlots();
        return ResponseEntity.ok(availabilitySlots);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addAvailabilitySlot(
            @PathVariable Long userId,
            @RequestBody AvailabilitySlotDTO availabilitySlotDTO) {
        try {
            AvailabilitySlotDTO newSlot = availabilitySlotService.addAvailabilitySlot(userId, availabilitySlotDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSlot);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/remove/{userId}/{slotId}")
    public ResponseEntity<String> removeAvailabilitySlot(
            @PathVariable Long userId,
            @PathVariable Long slotId) {
        try {
            availabilitySlotService.deleteAvailabilitySlot(userId, slotId);
            return ResponseEntity.ok("Availability slot removed successfully.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + userId);
        } catch (RuntimeException | AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AvailabilitySlotDTO>> getUserAvailabilitySlots(@PathVariable Long userId) {
        try {
            List<AvailabilitySlotDTO> userAvailabilitySlots = availabilitySlotService.getUserAvailabilitySlots(userId);
            return ResponseEntity.ok(userAvailabilitySlots);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/set-availability/{slotId}")
    public ResponseEntity<String> setAvailabilitySlotAvailability(
            @PathVariable Long slotId,
            @RequestParam boolean available) {
        try {
            availabilitySlotService.setAvailabilitySlotAvailability(slotId, available);
            return ResponseEntity.ok("Availability slot availability updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

