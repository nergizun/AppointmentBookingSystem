package com.nergiz.appointmentbookingsystem.repository;

import com.nergiz.appointmentbookingsystem.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nergiz.appointmentbookingsystem.model.AvailabilitySlot;

import java.util.List;


public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    List<AvailabilitySlot> findByUserAndAvailableIsTrue(User_ user);

    List<AvailabilitySlot> findByUserId(Long userId);
}
