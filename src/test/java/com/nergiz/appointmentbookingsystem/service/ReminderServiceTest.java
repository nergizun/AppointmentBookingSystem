package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.service.AppointmentService;
import com.nergiz.appointmentbookingsystem.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@EnableScheduling
public class ReminderServiceTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ReminderService reminderService;

    @Test
    public void testCheckUpcomingAppointments() {
        // Given
        ReflectionTestUtils.setField(reminderService, "appointmentService", appointmentService);
        ReflectionTestUtils.setField(reminderService, "notificationService", notificationService);

        // When
        reminderService.checkUpcomingAppointments();

        // Then
        verify(appointmentService).getUpcomingAppointments(15);
    }
}
