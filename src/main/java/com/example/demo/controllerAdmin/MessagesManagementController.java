package com.example.demo.controllerAdmin;

import com.example.demo.DTO.SimpleMessage;
import com.example.demo.model.Messages;
import com.example.demo.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "management/api/v1/messages")
public class MessagesManagementController {

    @Autowired
    MessagesService messagesService;

    @GetMapping
    private List<Messages> getAllMessages() {
        return messagesService.getAllMessages();
    }

    @DeleteMapping(path = "/delete/{messagesIds}")
    public void deleteMessages(@PathVariable("messagesIds") List<Long> ids) {
        messagesService.deleteMessages(ids);
    }

    @PutMapping (path = "/update")
    public void editMessage(@RequestBody SimpleMessage message) {
        messagesService.editMessage(message);
    }

    @PostMapping( path = "/add")
    public void addMessage(@RequestBody SimpleMessage message) {
        messagesService.addMessage(message);
    }

}
