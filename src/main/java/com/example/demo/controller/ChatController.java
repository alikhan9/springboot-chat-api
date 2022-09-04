package com.example.demo.controller;

import com.example.demo.model.GroupMessages;
import com.example.demo.model.Messages;
import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;


    @MessageMapping("/message")
    private GroupMessages receivePublicMessage(@Payload GroupMessages message) {
            return chatService.sendGroupMessage(message);
    }

    @MessageMapping("/private-message")
    public Messages receivePrivateMessage(@Payload Messages message) {
        return chatService.sendPrivateMessage(message);
    }

}
