package com.example.demo.service;

import com.example.demo.model.GroupMessages;
import com.example.demo.model.Groups;
import com.example.demo.model.Messages;
import com.example.demo.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class ChatService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    GroupMessagesService groupMessagesService;

    @Autowired
    MessagesService messagesService;

    @Autowired
    GroupsRepository groupsRepository;

    public Messages sendPrivateMessage(Messages message) {
        message.setDate(Timestamp.from(Instant.now()));
        messagesService.addMessage(message);
        simpMessagingTemplate.convertAndSendToUser(
                message.getReceiver().getUsername(),
                "/private", message);
        simpMessagingTemplate.convertAndSendToUser(
                message.getSender().getUsername(),
                "/private", message);

        return message;
    }

    public GroupMessages sendGroupMessage(GroupMessages message) {
        message.setDate(Timestamp.from(Instant.now()));
        Groups group = groupsRepository.findByNameSimple(message.getReceiver().getName());
        message.setReceiver(group);
        groupMessagesService.addGroupMessage(message);
        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getReceiver().getId(),message);
        return message;
    }
}
