package com.example.demo.GroupMessages;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.GroupMessages;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupMessagesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GroupMessagesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GroupMessagesServiceTest {

    private GroupMessagesService underTest;

    @Mock
    private GroupMessagesRepository groupMessagesRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new GroupMessagesService(groupMessagesRepository, userRepository);
    }

    @Test
    void canGetAllGroupsMessages() {
        // given
        // when
        underTest.getGroupsMessages();

        // then
        verify(groupMessagesRepository).findAll();
    }


    @Test
    void canGetGroupMessages() {
        // given
        Long groupId = 1l;
        given(groupMessagesRepository.getAllGroupMessages(groupId)).willReturn(Optional.of(new ArrayList<GroupMessages>()));

        // when
        underTest.getGroupMessages(groupId);

        // then
        verify(groupMessagesRepository).getAllGroupMessages(groupId);
    }

    @Test
    void willThrowWhenGivenGroupIdIsNotFound() {
        // given
        Long groupId = 1l;

        // when
        // then
        assertThatThrownBy(() -> underTest.getGroupMessages(groupId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("This id is not found in the database");
    }


    @Test
    void canGetUserGroupMessages() {
        // given
        Users user = new Users();
        user.setId(1l);

        // when
        underTest.getUserGroupMessages(user);

        // then
        verify(groupMessagesRepository).getGroupMessagesOfUserGroups(user.getId());
    }

    @Test
    void canAddGroupMessage() {
        // given
        Users user = new Users();
        GroupMessages groupMessages = new GroupMessages("", user, new Groups());
        given(userRepository.findByUsername(groupMessages.getSender().getUsername())).willReturn(Optional.of(user));

        // when
        underTest.addGroupMessage(groupMessages);

        // then
        verify(groupMessagesRepository).save(groupMessages);
    }

    @Test
    void willThrowWhenUserNotFoundForAddGroupMessage() {
        // given
        Users user = new Users();
        GroupMessages groupMessages = new GroupMessages("", user, new Groups());

        // when
        // then
        assertThatThrownBy(() -> underTest.addGroupMessage(groupMessages))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("This user does not exist");
        verify(groupMessagesRepository, never()).save(any());
    }

}






























