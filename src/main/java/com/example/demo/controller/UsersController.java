package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.model.UsersUsernameOnly;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( path = "api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private  PasswordEncoder passwordEncoder;

    @Autowired
    UsersService userService;

    @GetMapping( path = "/getUser")
    public Users getUser(@AuthenticationPrincipal Users user) {
        return user;
    }

    @PutMapping (path = "/update")
    public void updateUser(@Valid @RequestBody Users user) {
        userService.updateUser(user);
    }

    @PostMapping (path = "/add")
    public void addUser(@Valid @RequestBody Users user) {
        userService.addUser(user);
    }

    @DeleteMapping (path = "/delete/{userId}")
    public void deleteUser(@PathVariable("userId") List<Long> ids, Authentication authentication) {
        userService.deleteUser(ids);
    }

    @GetMapping( path = "/contacts")
    public List<String> getUserContacts(@AuthenticationPrincipal Users user) {
        return userService.getUserContacts(user.getId());
    }


    @GetMapping( path = "/usernames")
    public List<String> getUsersByUsername(@AuthenticationPrincipal Users user, @RequestParam("username") UsersUsernameOnly username) {
        return userService.getUsersByUsername(user.getId(),username.getUsername());
    }

}
