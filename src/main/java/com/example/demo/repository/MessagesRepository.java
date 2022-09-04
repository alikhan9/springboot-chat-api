package com.example.demo.repository;

import com.example.demo.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    @Query(value = "select * from messages where (sender = :id and receiver = :id2) or (sender = :id2 and receiver = :id)", nativeQuery = true)
    List<Messages> getUserMessagesFromOrToUser2(@Param("id") Long id, @Param("id2") Long id1);

    @Query(value =
            "select * from messages JOIN contacts on " +
            "(messages.sender = contacts.id_user1 and messages.receiver = contacts.id_user2) or " +
            "(messages.sender = contacts.id_user2 and messages.receiver = contacts.id_user1) " +
            " where sender = :idUser or receiver = :idUser"
            ,nativeQuery = true)
    List<Messages> getMessagesOfUserContacts(@Param("idUser") Long id);
}
