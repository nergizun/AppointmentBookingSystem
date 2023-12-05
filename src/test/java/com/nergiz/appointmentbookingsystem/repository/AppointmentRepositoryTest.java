package com.nergiz.appointmentbookingsystem.repository;

import com.nergiz.appointmentbookingsystem.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void testFindByStatus() {
        // Given
        User_ user = new User_();
        entityManager.persist(user);

        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setUser(user);
        entityManager.persist(slot);

        Appointment appointment1 = createAppointment(user, slot, AppointmentStatus.BOOKED);
        Appointment appointment2 = createAppointment(user, slot, AppointmentStatus.CANCELLED);

        entityManager.persist(appointment1);
        entityManager.persist(appointment2);
        entityManager.flush();

        // When
        List<Appointment> bookedAppointments = appointmentRepository.findByAppointmentStatus(AppointmentStatus.BOOKED);

        // Then
        assertEquals(1, bookedAppointments.size());
        assertEquals(appointment1.getId(), bookedAppointments.get(0).getId());
    }

    private Appointment createAppointment(User_ user, AvailabilitySlot slot, AppointmentStatus status) {
        Appointment appointment = new Appointment();
        appointment.setBookerUser(user);
        appointment.setAvailabilitySlot(slot);
        appointment.setAppointmentStatus(status);
        appointment.setStartTime(LocalDateTime.now());
        appointment.setEndTime(LocalDateTime.now().plusHours(1));

        Notification notification = new Notification();
        notification.setBookedAppointment(appointment);
        notification.setType(NotificationType.NEW_BOOKING);
        notification.setRecipientEmail("test@example.com");
        notification.setSubject("Test Subject");
        notification.setMessage("Test Message");
        notification.setSent(true);
        notification.setSentTime(LocalDateTime.now());

        appointment.getNotifications().add(notification);

        return appointment;
    }
}
