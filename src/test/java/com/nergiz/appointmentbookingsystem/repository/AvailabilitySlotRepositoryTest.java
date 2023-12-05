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
class AvailabilitySlotRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    @Test
    void testFindByUserAndAvailableIsTrue() {
        // Given
        User_ user = new User_();
        entityManager.persist(user);

        AvailabilitySlot availableSlot = createAvailabilitySlot(user, true);
        AvailabilitySlot unavailableSlot = createAvailabilitySlot(user, false);

        entityManager.persist(availableSlot);
        entityManager.persist(unavailableSlot);
        entityManager.flush();

        // When
        List<AvailabilitySlot> availableSlots = availabilitySlotRepository.findByUserAndAvailableIsTrue(user);

        // Then
        assertEquals(1, availableSlots.size());
        assertEquals(availableSlot.getId(), availableSlots.get(0).getId());
    }

    private AvailabilitySlot createAvailabilitySlot(User_ user, boolean available) {
        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setUser(user);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(LocalDateTime.now().plusHours(1));
        slot.setAvailable(available);

        return slot;
    }
}
