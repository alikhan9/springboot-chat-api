package com.example.demo.controller;

import com.example.demo.model.GroupMembers;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.service.GroupMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "/api/v1/groupMembers")
public class GroupMembersController {

    @Autowired
    GroupMembersService groupMembersService;

    @GetMapping
    private List<GroupMembers> getGroupMembers() {
        return groupMembersService.getGroupsMembers();
    }



    @PostMapping
    private void createGroupMembers(@RequestBody GroupMembers groupMembers) {
        groupMembersService.createGroupMembers(groupMembers);
    }

    @PostMapping( path = "/join")
    private void joinGroup(@AuthenticationPrincipal Users user, @RequestBody Groups group ) {
        groupMembersService.addUserToGroup(user.getId(),group.getId());
    }

    @PostMapping( path = "/leave")
    private void leaveGroup(@AuthenticationPrincipal Users user, @RequestBody Groups group ) {
        groupMembersService.removeUserFromGroup(user.getId(),group.getId());
    }


}
