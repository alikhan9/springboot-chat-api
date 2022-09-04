package com.example.demo.controller;

import com.example.demo.model.Messages;
import com.example.demo.model.Users;
import com.example.demo.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( path = "/api/v1/messages")
public class MessagesController {

    @Autowired
    MessagesService messagesService;

    @GetMapping ( path = "/specefic")
    private List<Messages> getMessagesOfUser(@AuthenticationPrincipal Users user, @RequestParam("username") String username) {
        return messagesService.getMessagesOfUser(user.getId(),username);
    }

    @GetMapping ( path = "personal")
    private List<Messages> getMessagesOfUserContacts(@AuthenticationPrincipal Users user) {
        return messagesService.getMessagesOfUserContacts(user.getId());
    }


}
