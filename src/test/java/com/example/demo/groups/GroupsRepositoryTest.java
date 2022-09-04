package com.example.demo.groups;

import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupsRepository;
import com.example.demo.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application.properties"
)
public class GroupsRepositoryTest {

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UserRepository userRepository;

    private final Faker faker = new Faker();


    @Test
    void itShouldGetAllGroupsWhereUserIsNotMember() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users user2Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();

        groupsRepository.save(new Groups(1L,"test",user2));
        groupsRepository.save(new Groups(2L,"public",user));
        groupsRepository.save(new Groups(3L,"What else",user));

        // when
        List<Groups> groups = groupsRepository.getGroupsByName(user.getId(), "test");

        // then
        assertThat(groups).hasSize(1);
        assertThat(groups.get(0).getOwner()).isNotEqualTo(user);

    }

    @Test
    void itShouldNotGetAnyGroups() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users user2Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();

        groupsRepository.save(new Groups(1L,"test",user));
        groupsRepository.save(new Groups(2L,"public",user));
        groupsRepository.save(new Groups(3L,"What else",user));

        // when
        List<Groups> groups = groupsRepository.getGroupsByName(user.getId(), "test");

        // then
        assertThat(groups).hasSize(0);
    }

    @Test
    void itShouldGetAllGroupsWhereUserIsTheOwner() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users user2Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();

        groupsRepository.save(new Groups(1L,"test",user2));
        groupsRepository.save(new Groups(2L,"public",user));
        groupsRepository.save(new Groups(3L,"What else",user));

        // when
        List<Groups> groups = groupsRepository.findGroupsByUserid(user.getId());

        // then
        assertThat(groups).hasSize(2);
        groups.stream().map(group -> assertThat(group.getOwner()).isEqualTo(user));
    }

    @Test
    void itShouldNotGetAnyGroupsWhereUserIsNotTheOwner() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users user2Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();

        groupsRepository.save(new Groups(1L,"test",user2));
        groupsRepository.save(new Groups(2L,"public",user2));
        groupsRepository.save(new Groups(3L,"What else",user2));

        // when
        List<Groups> groups = groupsRepository.findGroupsByUserid(user.getId());

        // then
        assertThat(groups).hasSize(0);
    }


    @Test
    void itShouldCheckThatGroupExistsByName() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );


        userRepository.save(userTemp);
        Users user = userRepository.findByUsername(userTemp.getUsername()).get();

        groupsRepository.save(new Groups(1L,"public",user));

        // when
        boolean result = groupsRepository.existsByName("public");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void itShouldCheckThatGroupDoesNotExistByName() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );


        userRepository.save(userTemp);
        Users user = userRepository.findByUsername(userTemp.getUsername()).get();

        groupsRepository.save(new Groups(1L,"public",user));

        // when
        boolean result = groupsRepository.existsByName("Test");

        // then
        assertThat(result).isFalse();
    }




}
