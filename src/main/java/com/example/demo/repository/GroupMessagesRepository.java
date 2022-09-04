package com.example.demo.repository;

import com.example.demo.model.GroupMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMessagesRepository extends JpaRepository<GroupMessages, Long> {

    @Query(value = "select * from group_messages where receiver in (select group_id from group_members where user_id = :userid) or receiver in (select id from groups where owner_id = :userid)", nativeQuery = true)
    List<GroupMessages> getGroupMessagesOfUserGroups(@Param("userid") Long userId);

    @Query(value = " select * from group_messages where receiver = :id", nativeQuery = true)
    Optional<List<GroupMessages>> getAllGroupMessages(@Param("id") Long id);
}
