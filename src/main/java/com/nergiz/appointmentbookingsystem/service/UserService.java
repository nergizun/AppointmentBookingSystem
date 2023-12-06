package com.nergiz.appointmentbookingsystem.service;

import com.nergiz.appointmentbookingsystem.dto.UserDTO;
import com.nergiz.appointmentbookingsystem.exception.UserNotFoundException;
import com.nergiz.appointmentbookingsystem.exception.UsernameNotUniqueException;
import com.nergiz.appointmentbookingsystem.model.User_;
import com.nergiz.appointmentbookingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User_> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long userId) {
        User_ existingUser = getUser(userId);
        return convertToDTO(existingUser);
    }
    public User_ getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        isUsernameUnique(userDTO.getUsername());

        log.info("USER CREATE");
        User_ user = convertToEntity(userDTO);
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
        User_ existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser = userRepository.save(existingUser);
        return convertToDTO(existingUser);

    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO convertToDTO(User_ user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .lastname(user.getLastname())
                .contactNumber(user.getContactNumber())
                .build();
    }

    public User_ convertToEntity(UserDTO userDTO) {
        return User_.builder()
                .username(userDTO.getUsername())
                .lastname(userDTO.getLastname())
                .name(userDTO.getName())
                .contactNumber(userDTO.getContactNumber())
                .email(userDTO.getEmail())
                .build();
    }

    public void isUsernameUnique(String username) {

        if (userRepository.findByUsername(username).isPresent()){
            throw new UsernameNotUniqueException(username);
        }
    }
}
