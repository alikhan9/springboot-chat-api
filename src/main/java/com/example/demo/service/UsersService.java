package com.example.demo.service;

import com.example.demo.config.PasswordConfig;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    @Autowired
    UserRepository userRepository;
    private final PasswordConfig passwordEncoder = new PasswordConfig();

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Users user) {
        Users actualUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new BadRequestException("User with id " + user.getId() + " is not in the database"));
        if(!actualUser.getEmail().equals(user.getEmail().trim()))
            if (userRepository.existsByEmail(user.getEmail().trim()))
                throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        if(!actualUser.getUsername().equals(user.getUsername().trim()))
            if (userRepository.existsByUsername(user.getUsername().trim()))
                throw new BadRequestException("User with username " + user.getUsername() + " already exists");
        user.setPassword(actualUser.getPassword());
        userRepository.save(user);
    }

    public void addUser(Users user) {
        if ( userRepository.existsByEmail(user.getEmail()))
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        if (userRepository.existsByUsername(user.getUsername()))
            throw new BadRequestException("User with username " + user.getUsername() + " already exists");
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(List<Long> ids) {
        ids.forEach(id -> {
            if (!userRepository.existsById(id))
                throw new BadRequestException("User with id " + id + " is not in the database");
            userRepository.deleteById(id);
        });

    }

    public List<String> getUserContacts(Long id) {
        return userRepository.getUserContacts(id);
    }

    public List<String> getUsersByUsername(Long userId, String username) {
        return userRepository.getAllUsersContactsByUsername(userId, username);
    }
}
