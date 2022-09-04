package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Messages;
import com.example.demo.model.Users;
import com.example.demo.repository.MessagesRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessagesService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessagesRepository repository;

    public void addMessage(Messages message) {
        message.setReceiver(userRepository.findByUsername(message.getReceiver().getUsername()).orElseThrow(() -> new BadRequestException("This receiver does not exist")));
        repository.save(message);
    }

    public List<Messages> getMessagesOfUser(Long id, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("This username is not in the database"));
        return repository.getUserMessagesFromOrToUser2(id, user.getId());
    }

    public List<Messages> getMessagesOfUserContacts(Long id) {
        return repository.getMessagesOfUserContacts(id);
    }
}
