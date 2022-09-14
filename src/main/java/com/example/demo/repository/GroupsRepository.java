package com.example.demo.repository;

import com.example.demo.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    @Query(value = "select * from groups where id not in (select group_id from group_members where user_id = :userId) and owner_id <> :userId and name ilike %:name%" , nativeQuery = true)
    List<Groups> getGroupsByName(@Param("userId") Long id, @Param("name") String name);

    @Query(value = "select * from groups where owner_id = :id or id in (select group_id from group_members where user_id = :id) ", nativeQuery = true)
    List<Groups> findGroupsByUserid(@Param("id") Long user_id);
    boolean existsByName(String name);

    @Query( value = "select * from groups where name = :name", nativeQuery = true)
    Groups findByNameSimple(@Param("name") String name);

    Optional<Groups> findByName(String name);
}
