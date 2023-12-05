package com.nergiz.appointmentbookingsystem.repository;

import com.nergiz.appointmentbookingsystem.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void testFindBySentIsFalse() {
        // Given
        User_ user = new User_();
        entityManager.persist(user);

        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setUser(user);
        entityManager.persist(slot);

        Appointment appointment = createAppointment(user, slot);
        Notification notification1 = createNotification(appointment, false);
        Notification notification2 = createNotification(appointment, true);

        entityManager.persist(notification1);
        entityManager.persist(notification2);
        entityManager.flush();

        // When
        List<Notification> unsentNotifications = notificationRepository.findBySentIsFalse();

        // Then
        assertEquals(1, unsentNotifications.size());
        assertEquals(notification1.getId(), unsentNotifications.get(0).getId());
    }

    private Appointment createAppointment(User_ user, AvailabilitySlot slot) {
        Appointment appointment = new Appointment();
        appointment.setBookerUser(user);
        appointment.setAvailabilitySlot(slot);
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        appointment.setStartTime(LocalDateTime.now());
        appointment.setEndTime(LocalDateTime.now().plusHours(1));

        return appointment;
    }

    private Notification createNotification(Appointment appointment, boolean sent) {
        // Save the Appointment before creating the Notification
        entityManager.persist(appointment);

        Notification notification = new Notification();
        notification.setBookedAppointment(appointment);
        notification.setType(NotificationType.NEW_BOOKING);
        notification.setRecipientEmail("test@example.com");
        notification.setSubject("Test Subject");
        notification.setMessage("Test Message");
        notification.setSent(sent);
        notification.setSentTime(LocalDateTime.now());

        return notification;
    }

}
