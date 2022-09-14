package com.example.demo.service;

import com.example.demo.DTO.SimpleContact;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Contacts;
import com.example.demo.model.Users;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactsService {

    @Autowired
    ContactsRepository repository;

    @Autowired
    UserRepository userRepository;

    public void addContact(Long id, String username) {
        Users contact = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("There is no user with username : " + username ));
        repository.save(new Contacts(id, contact.getId()));
    }

    public List<Contacts> getContacts(Long user_id) {
        userRepository.findById(user_id).orElseThrow(() -> new BadRequestException("This user does not exist"));
        return repository.getAllUserContacts(user_id);
    }


    public void removeContact(Long id, String username) {
        Users contact = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("There is no user with username : " + username ));
        repository.deleteContact(id, contact.getId());
    }

    public List<Contacts> getAllContacts() {
        return repository.findAll();
    }

    public void deleteContacts(List<Long> ids) {
        for(int i = 0; i < ids.size(); i+=2) {
            repository.deleteContact(ids.get(i),ids.get(i+1));
        }
    }

    public void addSimpleContact(SimpleContact contact) {
        userRepository.findById(contact.getUser1()).orElseThrow(() -> new BadRequestException("User1 does not exist"));
        userRepository.findById(contact.getUser2()).orElseThrow(() -> new BadRequestException("User2 does not exist"));
        repository.save(new Contacts(contact.getUser1(), contact.getUser2()));
    }

    public void updateSimpleContact(SimpleContact contact) {
        userRepository.findById(contact.getUser1()).orElseThrow(() -> new BadRequestException("User1 does not exist"));
        userRepository.findById(contact.getUser2()).orElseThrow(() -> new BadRequestException("User2 does not exist"));
        userRepository.findById(contact.getOldUser2()).orElseThrow(() -> new BadRequestException("OldUser2 does not exist"));
        repository.delete(new Contacts(contact.getUser1(), contact.getOldUser2()));
        repository.save(new Contacts(contact.getUser1(), contact.getUser2()));
    }
}
