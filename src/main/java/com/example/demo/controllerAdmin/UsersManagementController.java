package com.example.demo.controllerAdmin;

import com.example.demo.DTO.SimpleUser;
import com.example.demo.model.Users;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( path = "management/api/v1/users")
@RequiredArgsConstructor
public class UsersManagementController {

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    UsersService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(path = "/update")
    public void updateUser(@Valid @RequestBody Users user) {
        userService.updateUser(user);
    }


    @PostMapping(path = "/createAdmin")
    public void createAdminUser(@Valid @RequestBody Users user) {
        userService.createAdminUser(user);
    }

    @PostMapping (path = "/add")
    public void addUser(@Valid @RequestBody Users user) {
        userService.addUser(user);
    }

    @DeleteMapping (path = "/delete/{usersIds}")
    public void deleteUsers(@PathVariable("usersIds") List<Long> ids) {
        userService.deleteUser(ids);
    }

    @GetMapping (path = "/usernames")
    public List<SimpleUser> getUsernames() {
        return userService.getUsernames();
    }
}
