package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.GroupMembers;
import com.example.demo.repository.GroupMembersRepository;
import com.example.demo.repository.GroupsRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupMembersService {


    @Autowired
    GroupMembersRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupsRepository groupsRepository;

    public List<GroupMembers> getGroupsMembers() {
        return repository.findAll();
    }



    public void createGroupMembers(GroupMembers groupMembers) {
        userRepository.findById(groupMembers.getUser_id()).orElseThrow(() -> new BadRequestException("This user does not exist"));
        groupsRepository.findById(groupMembers.getGroup_id()).orElseThrow(() -> new BadRequestException("This group does not exist"));
        repository.save(groupMembers);
    }

    public void addUserToGroup(Long user_id, Long group_id) {
        userRepository.findById(user_id).orElseThrow(() -> new BadRequestException("This user does not exist"));
        groupsRepository.findById(group_id).orElseThrow(() -> new BadRequestException("This group does not exist"));
        repository.save(new GroupMembers(group_id, user_id));
    }

    public void removeUserFromGroup(Long user_id, Long group_id) {
        userRepository.findById(user_id).orElseThrow(() -> new BadRequestException("This user does not exist"));
        groupsRepository.findById(group_id).orElseThrow(() -> new BadRequestException("This group does not exist"));
        repository.delete(new GroupMembers(group_id, user_id));
    }
}
