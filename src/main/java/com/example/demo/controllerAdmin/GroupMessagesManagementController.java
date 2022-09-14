package com.example.demo.controllerAdmin;

import com.example.demo.DTO.SimpleMessage;
import com.example.demo.model.GroupMessages;
import com.example.demo.service.GroupMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "management/api/v1/groupMessages")
public class GroupMessagesManagementController {

    @Autowired
    GroupMessagesService groupMessagesService;

    @GetMapping
    private List<GroupMessages> getAllGroupsMessages() {
        return groupMessagesService.getAllGroupMessages();
    }

    @PutMapping( path = "/update")
    private void updateGroupMessage(@RequestBody SimpleMessage groupMessage) {
        groupMessagesService.updateGroupMessage(groupMessage);
    }

    @PostMapping(path = "/add")
    private void addGroupMessage(@RequestBody SimpleMessage groupMessage) {
        groupMessagesService.addSimpleGroupMessage(groupMessage);
    }


    @DeleteMapping(path = "/delete/{groupsMessagesIds}")
    public void deleteUser(@PathVariable("groupsMessagesIds") List<Long> ids) {
        groupMessagesService.deleteGroupsMessages(ids);
    }
}
