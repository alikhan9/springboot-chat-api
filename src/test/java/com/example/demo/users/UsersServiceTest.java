package com.example.demo.users;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UserRepository userRepository;
    private UsersService underTest;
    @Mock
    private  PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new UsersService(userRepository);
    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void canUsersByUsername() {
        // when
        underTest.getUsersByUsername(1l, "user1");
        // then
        verify(userRepository).getAllUsersContactsByUsername(1l, "user1");
    }

    @Test
    void canUpdateUser() {

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
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // when
        underTest.updateUser(user);

        // then
        ArgumentCaptor<Users> usersArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository).save(usersArgumentCaptor.capture());
        assertThat(usersArgumentCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void canUpdateUserWhenEmailChanged() {
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
        Users updatedUser = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "johnyyyy.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.existsByEmail(updatedUser.getEmail())).willReturn(false);
        // when
        underTest.updateUser(updatedUser);

        // then
        ArgumentCaptor<Users> usersArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository).save(usersArgumentCaptor.capture());
        assertThat(usersArgumentCaptor.getValue()).isEqualTo(updatedUser);
    }

    @Test
    void canUpdateUserWhenUsernameChanged() {
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
        Users updatedUser = new Users(
                1L,
                "john49000",
                "John",
                "Dao",
                "john.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.existsByUsername(updatedUser.getUsername())).willReturn(false);
        // when
        underTest.updateUser(updatedUser);

        // then
        ArgumentCaptor<Users> usersArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository).save(usersArgumentCaptor.capture());
        assertThat(usersArgumentCaptor.getValue()).isEqualTo(updatedUser);
    }

    @Test
    void WillThrowWhenUserDoesNotExistForUpdateUser() {

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
        // then
        assertThatThrownBy(() -> underTest.updateUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User with id " + user.getId() + " is not in the database");
        verify(userRepository, never()).save(any());

    }

    @Test
    void WillThrowWhenUserNewEmailIsTakenForUpdateUser() {

        // given
        Users user = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "johny.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );
        Users updatedUser = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "johnyyyy.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.existsByEmail(updatedUser.getEmail())).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.updateUser(updatedUser))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User with email " + updatedUser.getEmail() + " already exists");
        verify(userRepository, never()).save(any());

    }

    @Test
    void WillThrowWhenUserNewUsernameIsTakenForUpdateUser() {

        // given
        Users user = new Users(
                1L,
                "john49",
                "John",
                "Dao",
                "johny.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );
        Users updatedUser = new Users(
                1L,
                "johny490",
                "John",
                "Dao",
                "johny.dao@gmail.com",
                LocalDate.now(),
                "123456"
        );

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.existsByUsername(updatedUser.getUsername())).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.updateUser(updatedUser))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User with username " + updatedUser.getUsername() + " already exists");
        verify(userRepository, never()).save(any());

    }

    @Test
    void canAddUser() {
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
        underTest.addUser(user);

        // then
        ArgumentCaptor<Users> usersArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository).save(usersArgumentCaptor.capture());
        assertThat(usersArgumentCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void WillThrowWhenUsernameIsTakenForAddUser() {
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

        given(userRepository.existsByUsername(user.getUsername())).willReturn(true);

        //when
        // then
        assertThatThrownBy(() -> underTest.addUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User with username " + user.getUsername() + " already exists");
        verify(userRepository, never()).save(any());
    }

    @Test
    void WillThrowWhenEmailIsTakenForAddUser() {
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

        given(userRepository.existsByEmail(user.getEmail())).willReturn(true);

        //when
        // then
        assertThatThrownBy(() -> underTest.addUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User with email " + user.getEmail() + " already exists");
        verify(userRepository, never()).save(any());
    }

    @Test
    void canDeleteUser() {
        // given
        Long id = 1L;
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        given(userRepository.existsById(id)).willReturn(true);

        // when
        underTest.deleteUser(ids);

        // then
        ArgumentCaptor<Long> usersArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).deleteById(usersArgumentCaptor.capture());
        assertThat(usersArgumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void WillThrowWhenUserDoesNotExistForDeleteUser() {
        // given
        Long id = 1L;
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        given(userRepository.existsById(id)).willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteUser(Collections.singletonList(ids.get(0))))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User with id 1 is not in the database");
        verify(userRepository, never()).deleteById(any());
    }
}