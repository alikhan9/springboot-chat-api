package com.example.demo.groups;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GroupsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GroupsServiceTest {

    @Mock
    private GroupsRepository groupsRepository;

    @Mock
    private UserRepository userRepository;

    private GroupsService underTest;

    @BeforeEach
    void setUp() {
        underTest = new GroupsService(groupsRepository, userRepository);
    }

    @Test
    void canGetAllUserGroups() {
        // given
        Users user = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "john.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );

        // when
        underTest.getUserGroups(user);

        // then
        verify(groupsRepository).findGroupsByUserid(user.getId());
    }

    @Test
    void canDeleteGroup() {
        // given
        Long groupId = 1L;
        // when
        underTest.deleteGroup(groupId);

        // then
        verify(groupsRepository).deleteById(groupId);
    }

    @Test
    void canGetUnsubscribedGroups(){
        // given
        Long userId = 1l;
        String groupName = "test-group";

        // when
        underTest.getUnsubscribedGroups(userId, groupName);

        // then
        verify(groupsRepository).getGroupsByName(userId, groupName);
    }

    @Test
    void canCreateGroup() {
        Users owner = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "john.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );
        Groups group = new Groups(1L, "test", owner);
        given(userRepository.findById(group.getOwner().getId())).willReturn(Optional.of(owner));
        given(groupsRepository.existsByName(group.getName())).willReturn(false);

        // when
        underTest.createGroup(group);

        // then
        verify(groupsRepository).save(group);
    }

    @Test
    void canNotCreateGroupWhenNameIsTaken() {
        Users owner = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "john.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );
        Groups group = new Groups(1L, "test", owner);
        given(userRepository.findById(group.getOwner().getId())).willReturn(Optional.of(owner));
        given(groupsRepository.existsByName(group.getName())).willReturn(true);

        // when
        // then
        assertThatThrownBy(() ->underTest.createGroup(group))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This group name is taken");
        verify(groupsRepository, never()).save(any());
    }

    @Test
    void canNotCreateGroupWhenNameIsEmpty() {
        Users owner = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "john.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );
        Groups group = new Groups(1L, "", owner);
        given(userRepository.findById(group.getOwner().getId())).willReturn(Optional.of(owner));

        // when
        // then
        assertThatThrownBy(() ->underTest.createGroup(group))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Group name cannot be empty");
        verify(groupsRepository, never()).save(any());
    }


}
