package com.example.demo.controllerAdmin;

import com.example.demo.DTO.SimpleContact;
import com.example.demo.model.Contacts;
import com.example.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "management/api/v1/contacts")
public class ContactsManagementController {

    @Autowired
    private ContactsService contactsService;

    @GetMapping
    private List<Contacts> getContacts() {
        return contactsService.getAllContacts();
    }

    @DeleteMapping(path = "/delete/{contactsIds}")
    private void deleteUser(@PathVariable("contactsIds") List<Long> ids) {
        contactsService.deleteContacts(ids);
    }

    @PostMapping(path = "/add")
    private void addContact(@RequestBody SimpleContact contact) {
        contactsService.addSimpleContact(contact);
    }

    @PutMapping(path = "/update")
    private void updateContact(@RequestBody SimpleContact contact) {
        contactsService.updateSimpleContact(contact);
    }
}
