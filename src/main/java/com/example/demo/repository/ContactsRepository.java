package com.example.demo.repository;

import com.example.demo.model.Contacts;
import com.example.demo.model.ContactsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, ContactsId> {

    @Query(value = "select * from contacts where id_user1 = :id or id_user2 = :id", nativeQuery = true)
    List<Contacts> getAllUserContacts(@Param("id") Long user_id);

    @Modifying
    @Transactional
    @Query(value = "delete from contacts where (id_user1 = :userId and id_user2 = :contactId) or (id_user2 = :userId and id_user1 = :contactId)", nativeQuery = true)
    int deleteContact(@Param("userId") Long id, @Param("contactId") Long id1);
}
