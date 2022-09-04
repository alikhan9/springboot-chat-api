package com.example.demo.repository;

import com.example.demo.model.GroupMembers;
import com.example.demo.model.GroupMembersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, GroupMembersId> {
}
