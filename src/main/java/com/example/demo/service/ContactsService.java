package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Contacts;
import com.example.demo.model.Users;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
