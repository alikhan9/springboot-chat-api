package com.example.demo.Contacts;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Users;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ContactsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ContactsServiceTest {

    private ContactsService underTest;

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new ContactsService(contactsRepository, userRepository);
    }


    @Test
    void canGetUserContacts() {
        // given
        Long userId = 1l;

        given( userRepository.findById(userId)).willReturn(Optional.of(new Users()));

        // when
        underTest.getContacts(userId);

        // then
        verify(contactsRepository).getAllUserContacts(userId);
    }


    @Test
    void willThrowWhenUserNotFoundForGetUserContacts() {
        // given
        Long userId = 1l;

        // when
        // then
        assertThatThrownBy(() -> underTest.getContacts(userId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("This user does not exist");
        verify(contactsRepository, never()).getAllUserContacts(any());
    }


    @Test
    void canAddContacts() {
        // given
        Long userId = 1l;
        String username = "username";

        given( userRepository.findByUsername(username)).willReturn(Optional.of(new Users()));

        // when
        underTest.addContact(userId, username);

        // then
        verify(contactsRepository).save(any());
    }

    @Test
    void wilThrowWhenGivenUsernameIsNotFoundForAddContacts() {
        // given
        Long userId = 1l;
        String username = "username";

        // when
        // then
        assertThatThrownBy(() -> underTest.addContact(userId, username))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("There is no user with username : " + username);
        verify(contactsRepository, never()).save(any());
    }

    @Test
    void canRemoveContact() {
        // given
        Long userId = 1l;
        String username = "username";

        Users contactToRemove = new Users();
        contactToRemove.setUsername(username);
        contactToRemove.setId(1l);

        given( userRepository.findByUsername(username)).willReturn(Optional.of(contactToRemove));

        // when
        underTest.removeContact(userId, username);

        // then
        verify(contactsRepository).deleteContact(userId, contactToRemove.getId());
    }


    @Test
    void wilThrowWhenGivenUsernameIsNotFoundForRemoveContact() {
        // given
        Long userId = 1l;
        String username = "username";

        // when
        // then
        assertThatThrownBy(() -> underTest.removeContact(userId, username))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("There is no user with username : " + username);
        verify(contactsRepository, never()).deleteContact(any(), any());
    }

}
