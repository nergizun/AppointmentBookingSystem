package com.nergiz.appointmentbookingsystem.repository;

import com.nergiz.appointmentbookingsystem.model.Appointment;
import com.nergiz.appointmentbookingsystem.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentStatus(AppointmentStatus appointmentStatus);

    @Query("SELECT a FROM Appointment a " +
            "JOIN a.availabilitySlot s " +
            "WHERE a.bookerUser.id = :userId OR s.user.id = :userId")
    List<Appointment> findByBookerUserIdOrSlotUserId(@Param("userId") Long userId);

    List<Appointment> findByStartTimeBetweenAndAppointmentStatus(LocalDateTime now, LocalDateTime endTime, AppointmentStatus appointmentStatus);

    List<Appointment> findByStartTimeBeforeAndAppointmentStatus(LocalDateTime startTime, AppointmentStatus appointmentStatus);
}
