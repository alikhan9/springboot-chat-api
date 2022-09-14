package com.example.demo.controller;

import com.example.demo.model.Contacts;
import com.example.demo.model.Users;
import com.example.demo.DTO.UsersUsernameOnly;
import com.example.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/contacts")
public class ContactsController {

    @Autowired
    ContactsService contactsService;

    @GetMapping
    private List<Contacts> getContacts(@AuthenticationPrincipal Users user) {
        return contactsService.getContacts(user.getId());
    }

    @PostMapping
    private void addContact(@AuthenticationPrincipal Users user, @RequestBody UsersUsernameOnly username) {
        contactsService.addContact(user.getId(), username.getUsername());
    }

    @DeleteMapping
    private void removeContact(@AuthenticationPrincipal Users user, @RequestBody UsersUsernameOnly username) {
        contactsService.removeContact(user.getId(), username.getUsername());
    }

}
