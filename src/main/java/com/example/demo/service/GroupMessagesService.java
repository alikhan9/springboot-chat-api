package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.GroupMessages;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupMessagesRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupMessagesService {

    @Autowired
    GroupMessagesRepository repository;

    @Autowired
    UserRepository userRepository;

    public void addGroupMessage(GroupMessages groupMessages) {
        Users u = userRepository.findByUsername(groupMessages.getSender().getUsername()).orElseThrow(() -> new BadRequestException("This user does not exist"));
        groupMessages.setSender(u);
        repository.save(groupMessages);
    }

    public List<GroupMessages> getGroupsMessages() {
        return repository.findAll();
    }

    public List<GroupMessages> getUserGroupMessages(Users user) {
        return repository.getGroupMessagesOfUserGroups(user.getId());
    }

    public List<GroupMessages> getGroupMessages(Long id) {
        return repository.getAllGroupMessages(id).orElseThrow(() -> new BadRequestException("This id is not found in the database"));
    }
}
