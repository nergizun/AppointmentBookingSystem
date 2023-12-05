package com.nergiz.appointmentbookingsystem.repository;

import com.nergiz.appointmentbookingsystem.model.User_;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        // Given
        User_ user = new User_();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // When
        User_ savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("testPassword", savedUser.getPassword());
    }

    @Test
    void testGetUserById() {
        // Given
        User_ user = new User_();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        User_ savedUser = userRepository.save(user);

        // When
        Optional<User_> retrievedUserOptional = userRepository.findById(savedUser.getId());

        // Then
        assertTrue(retrievedUserOptional.isPresent());
        User_ retrievedUser = retrievedUserOptional.get();
        assertEquals("testUser", retrievedUser.getUsername());
        assertEquals("testPassword", retrievedUser.getPassword());
    }
}
