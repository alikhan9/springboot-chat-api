package com.example.demo.controller;

import com.example.demo.model.GroupMessages;
import com.example.demo.model.Users;
import com.example.demo.service.GroupMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( path = "/api/v1/groupMessages")
public class GroupMessagesController {

    @Autowired
    GroupMessagesService groupMessagesService;

    @GetMapping
    private List<GroupMessages> getGroupsMessages(){
        return groupMessagesService.getGroupsMessages();
    }

    @GetMapping( path = "/personal")
    private List<GroupMessages> getUserGroupMessages(@AuthenticationPrincipal Users user){
        return groupMessagesService.getUserGroupMessages(user);
    }

    @GetMapping( path = "/specefic")
    private List<GroupMessages> getGroupMessagesById(@RequestParam("id") Long id) {
        return groupMessagesService.getGroupMessages(id);
    }


}
