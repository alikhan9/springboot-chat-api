package com.example.demo.service;

import com.example.demo.DTO.SimpleMessage;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.GroupMessages;
import com.example.demo.model.Groups;
import com.example.demo.model.Messages;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupMessagesRepository;
import com.example.demo.repository.GroupsRepository;
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

    @Autowired
    GroupsRepository groupsRepository;

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

    public List<GroupMessages> getAllGroupMessages() {
        return repository.findAll();
    }

    public void updateGroupMessage(SimpleMessage groupMessage) {
        Users sender = userRepository.findByUsername(groupMessage.getSender()).orElseThrow(() -> new BadRequestException("This sender does not exist"));
        Groups receiver = groupsRepository.findByName(groupMessage.getReceiver()).orElseThrow(() -> new BadRequestException("This group does not exist"));
        repository.save(new GroupMessages(groupMessage.getId(), groupMessage.getMessage(), sender, receiver, groupMessage.getDate()));
    }

    public void addSimpleGroupMessage(SimpleMessage groupMessage) {
        Users sender = userRepository.findByUsername(groupMessage.getSender()).orElseThrow(() -> new BadRequestException("This sender does not exist"));
        Groups receiver = groupsRepository.findByName(groupMessage.getReceiver()).orElseThrow(() -> new BadRequestException("This group does not exist"));
        repository.save(new GroupMessages(groupMessage.getMessage(), sender, receiver));
    }

    public void deleteGroupsMessages(List<Long> ids) {
        repository.deleteAllByIdInBatch(ids);
    }
}
















