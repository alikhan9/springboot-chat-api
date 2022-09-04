package com.example.demo.messages;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Messages;

import com.example.demo.model.Users;
import com.example.demo.repository.MessagesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MessagesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessagesServiceTest {

    @Mock
    private MessagesRepository messagesRepository;
    @Mock
    private UserRepository userRepository;

    private MessagesService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MessagesService(userRepository, messagesRepository);
    }

    @Test
    void canGetMessagesOfUserContacts() {
        // given
        Long userId = 1L;
        // when
        underTest.getMessagesOfUserContacts(userId);

        // then
        verify(messagesRepository).getMessagesOfUserContacts(userId);
    }

    @Test
    void canAddMessage() {
        // given
        Messages message = new Messages(1L,new Users(), new Users(), "Ok", Timestamp.from(Instant.now()));
        given(userRepository.findByUsername(message.getReceiver().getUsername())).willReturn(Optional.of(new Users()));

        // when
        underTest.addMessage(message);

        // then
        verify(messagesRepository).save(message);
    }

    @Test
    void WillThrowWhenReceiverIsNotInTheDatabase() {
        // given
        Messages message = new Messages(1L,new Users(), new Users(), "Ok", Timestamp.from(Instant.now()));
        message.getReceiver().setUsername("test");

        // when
        // then
        assertThatThrownBy(() ->  underTest.addMessage(message))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This receiver does not exist");
        verify(messagesRepository, never()).save(any());
    }

    @Test
    void canGetMessageOfUserToAnOtherUser() {
        // given
        Long userId = 1L;
        Users user2 = new Users();
        user2.setUsername("test2");

        given(userRepository.findByUsername(user2.getUsername())).willReturn(Optional.of(user2));

        // when
        underTest.getMessagesOfUser(userId, user2.getUsername());

        // then
        verify(messagesRepository).getUserMessagesFromOrToUser2(userId, user2.getId());
    }


    @Test
    void willThrowWhenGivenUsernameIsNotInTheDatabase() {
        // given
        Long userId = 1L;
        Users user2 = new Users();
        user2.setUsername("test2");

        // when
        assertThatThrownBy(() ->  underTest.getMessagesOfUser(userId, user2.getUsername()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This username is not in the database");
        verify(messagesRepository, never()).getUserMessagesFromOrToUser2(any(), any());
    }

}
