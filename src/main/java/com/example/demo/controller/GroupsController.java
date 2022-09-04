package com.example.demo.controller;

import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "/api/v1/groups")
public class GroupsController {

    @Autowired
    GroupsService groupsService;

    @PostMapping
    private void createGroup(@RequestBody Groups group) {
        groupsService.createGroup(group);
    }

    @GetMapping( path = "/personal")
    private List<Groups> getGroupsOfUser(@AuthenticationPrincipal Users user) {
        return groupsService.getUserGroups(user);
    }

    @GetMapping( path = "/specefic")
    private List<Groups> getUnsubscribedGroups(@RequestParam("name") String name, @AuthenticationPrincipal Users user) {
        return groupsService.getUnsubscribedGroups(user.getId(),name);
    }

    @DeleteMapping
    private void deleteGroup(@RequestParam("id")Long groupId) {
        groupsService.deleteGroup(groupId);
    }

}
