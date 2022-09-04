package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<Users, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    Long deleteByUsername(String username);

    Optional<Users> findByUsername(String username);

    @Query(value = "select username from users where id in (select id_user1 from contacts where id_user2 = :id) or id in (select id_user2 from contacts where id_user1 = :id)", nativeQuery = true)
    List<String> getUserContacts(@Param("id") Long id);

    @Query(value =
            "select username from users " +
            "where id not in (select id_user2 from contacts where id_user1 = :userId) " +
            "and id not in (select id_user1 from contacts where id_user2 = :userId) " +
            "and username ilike %:username% and id <> :userId"
            , nativeQuery = true)
    List<String> getAllUsersContactsByUsername(@Param("userId") Long username, @Param("username") String s);
}
