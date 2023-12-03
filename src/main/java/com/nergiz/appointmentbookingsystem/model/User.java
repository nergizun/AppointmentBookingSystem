package com.nergiz.appointmentbookingsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String lastname;
    private String contactNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AvailabilitySlot> availabilitySlots;

    @OneToMany(mappedBy = "bookerUser", cascade = CascadeType.ALL)
    private List<Appointment> bookedAppointments;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // getters and setters
}
