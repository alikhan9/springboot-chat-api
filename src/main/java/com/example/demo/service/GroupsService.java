package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupsRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupsService {

    @Autowired
    GroupsRepository repository;

    @Autowired
    UserRepository userRepository;

    public void createGroup(Groups group) {
        userRepository.findById(group.getOwner().getId()).orElseThrow(() -> new BadRequestException("This user does not exist"));
        if(group.getName().isEmpty())
            throw new BadRequestException("Group name cannot be empty");
        if(repository.existsByName(group.getName()))
            throw new BadRequestException("This group name is taken");
        repository.save(group);
    }

    public List<Groups> getUnsubscribedGroups(Long id, String name) {
        return repository.getGroupsByName(id, name);
    }

    public List<Groups> getUserGroups(Users user) {
        return repository.findGroupsByUserid(user.getId());
    }

    public void deleteGroup(Long groupId) {
        repository.deleteById(groupId);
    }
}
