package com.nergiz.appointmentbookingsystem.controller;

import com.nergiz.appointmentbookingsystem.dto.AppointmentDTO;
import com.nergiz.appointmentbookingsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long appointmentId) {
        try {
            AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.ok(appointmentDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments(@PathVariable Long userId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getUserAppointments(userId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            @RequestParam Long slotId,
            @RequestParam Long bookerUserId) {
        try {
            AppointmentDTO appointmentDTO = appointmentService.bookAppointment(slotId, bookerUserId);
            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelAppointment(
            @RequestParam Long appointmentId,
            @RequestParam Long userId) {
        try {
            appointmentService.cancelAppointment(appointmentId, userId);
            return ResponseEntity.ok("Appointment cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
