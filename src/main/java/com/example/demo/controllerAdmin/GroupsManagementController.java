package com.example.demo.controllerAdmin;

import com.example.demo.DTO.SimpleGroupWithUserId;
import com.example.demo.DTO.SimpleGroupWithUserUsername;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "management/api/v1/groups")
public class GroupsManagementController {

    @Autowired
    GroupsService groupsService;

    @GetMapping
    private List<Groups> groups() {
        return groupsService.getAllGroups();
    }

    @DeleteMapping(path = "/delete/{groupsIds}")
    private void deleteUser(@PathVariable("groupsIds") List<Long> ids) {
        groupsService.deleteGroups(ids);
    }

    @PutMapping(path = "/update")
    private void updateUser(@RequestBody SimpleGroupWithUserUsername group) {
        groupsService.updateGroup(group);
    }

    @PostMapping(path = "/add")
    private void createGroup(@RequestBody SimpleGroupWithUserId group) {
        Users owner = new Users();
        owner.setId(group.getOwner());
        groupsService.createGroup(new Groups(group.getName(), owner));
    }

}
