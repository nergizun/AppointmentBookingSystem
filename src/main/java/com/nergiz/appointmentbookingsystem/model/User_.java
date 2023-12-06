package com.nergiz.appointmentbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User_ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String name;
    private String lastname;
    private String contactNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AvailabilitySlot> availabilitySlots;

    @OneToMany(mappedBy = "bookerUser", cascade = CascadeType.ALL)
    private List<Appointment> bookedAppointments;

    @Override
    public String toString() {
        return "User_{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                // Other fields excluding references to AvailabilitySlot
                '}';
    }

}
