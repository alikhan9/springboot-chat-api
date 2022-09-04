package com.example.demo.GroupMembers;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.GroupMembers;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupMembersRepository;
import com.example.demo.repository.GroupsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GroupMembersService;
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
public class GroupMembersServiceTest {

    private GroupMembersService underTest;

    @Mock
    private GroupMembersRepository groupMembersRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupsRepository groupsRepository;

    @BeforeEach
    void setUp() {
        underTest = new GroupMembersService(groupMembersRepository, userRepository, groupsRepository);
    }

    @Test
    void canGetGroupsMembers() {
        // given
        // when
        underTest.getGroupsMembers();

        // then
        verify(groupMembersRepository).findAll();
    }


    @Test
    void canCreateGroupMembers() {
        // given
        Users user1 = new Users();
        Users user2 = new Users();

        user1.setId(1l);
        user2.setId(2l);

        Groups group = new Groups(1L,"test", user1);

        GroupMembers groupMembers = new GroupMembers(group.getId(), user1.getId());

        given(userRepository.findById(groupMembers.getUser_id())).willReturn(Optional.of(user1));
        given(groupsRepository.findById(groupMembers.getGroup_id())).willReturn(Optional.of(group));

        // when
        underTest.createGroupMembers(groupMembers);

        // then
        verify(groupMembersRepository).save(groupMembers);

    }

    @Test
    void willThrowWhenUserNotFoundForCreateGroupMembers() {
        // given
        Users user1 = new Users();
        Users user2 = new Users();

        user1.setId(1l);
        user2.setId(2l);

        Groups group = new Groups(1L,"test", user1);

        GroupMembers groupMembers = new GroupMembers(group.getId(), user1.getId());

        // when
        // then
        assertThatThrownBy(() ->  underTest.createGroupMembers(groupMembers))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This user does not exist");
        verify(groupMembersRepository, never()).save(any());
    }

    @Test
    void willThrowWhenGroupNotFoundForCreateGroupMembers() {
        // given
        Users user1 = new Users();
        Users user2 = new Users();

        user1.setId(1l);
        user2.setId(2l);

        Groups group = new Groups(1L,"test", user1);

        GroupMembers groupMembers = new GroupMembers(group.getId(), user1.getId());
        given(userRepository.findById(groupMembers.getUser_id())).willReturn(Optional.of(user1));

        // when
        // then
        assertThatThrownBy(() ->  underTest.createGroupMembers(groupMembers))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This group does not exist");
        verify(groupMembersRepository, never()).save(any());
    }


    @Test
    void canAddUserToGroup() {
        // given
        Long userId = 1l;
        Long groupId = 1l;

        given(userRepository.findById(userId)).willReturn(Optional.of(new Users()));
        given(groupsRepository.findById(groupId)).willReturn(Optional.of(new Groups()));

        // when
        underTest.addUserToGroup(userId, groupId);

        // then
        verify(groupMembersRepository).save(any());
    }

    @Test
    void willThrowWhenUserNotFoundForAddUserToGroup() {
        // given
        Long userId = 1l;
        Long groupId = 1l;

        // when
        // then
        assertThatThrownBy(() ->  underTest.addUserToGroup(userId, groupId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This user does not exist");
        verify(groupMembersRepository, never()).save(any());
    }

    @Test
    void willThrowWhenGroupNotFoundForAddUserToGroup() {
        // given
        Long userId = 1l;
        Long groupId = 1l;

        given(userRepository.findById(userId)).willReturn(Optional.of(new Users()));

        // when
        // then
        assertThatThrownBy(() ->  underTest.addUserToGroup(userId, groupId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This group does not exist");
        verify(groupMembersRepository, never()).save(any());
    }


    @Test
    void canRemoveUserFromGroup() {
        // given
        Long userId = 1l;
        Long groupId = 1l;

        given(userRepository.findById(userId)).willReturn(Optional.of(new Users()));
        given(groupsRepository.findById(groupId)).willReturn(Optional.of(new Groups()));

        // when
        underTest.removeUserFromGroup(userId, groupId);

        // then
        verify(groupMembersRepository).delete(any());
    }

    @Test
    void willThrowWhenUserNotFoundForRemoveUserFromGroup() {
        // given
        Long userId = 1l;
        Long groupId = 1l;

        // when
        // then
        assertThatThrownBy(() ->  underTest.removeUserFromGroup(userId, groupId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This user does not exist");
        verify(groupMembersRepository, never()).save(any());
    }

    @Test
    void willThrowWhenGroupNotFoundForRemoveUserFromGroup() {
        // given
        Long userId = 1l;
        Long groupId = 1l;

        given(userRepository.findById(userId)).willReturn(Optional.of(new Users()));

        // when
        // then
        assertThatThrownBy(() ->  underTest.removeUserFromGroup(userId, groupId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This group does not exist");
        verify(groupMembersRepository, never()).save(any());
    }


}


























