package com.example.demo.GroupMessages;

import com.example.demo.model.GroupMessages;
import com.example.demo.model.Groups;
import com.example.demo.model.Users;
import com.example.demo.repository.GroupMessagesRepository;
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
public class GroupMessagesRepositoryTest {

    @Autowired
    private GroupMessagesRepository groupMessagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    private final Faker faker = new Faker();


    @Test
    void shouldGetAllGroupMessagesThatUserIsPartOf() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users userTemp2 = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(userTemp2);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(userTemp2.getUsername()).get();

        Groups groupTemp = new Groups("public", user);
        Groups group2Temp = new Groups("Test", user);

        groupsRepository.save(groupTemp);
        groupsRepository.save(group2Temp);

        Groups group = groupsRepository.findByNameSimple(groupTemp.getName());
        Groups group2 = groupsRepository.findByNameSimple(group2Temp.getName());

        for( int i = 0; i < 8; i++ ) {
            if(i%2 == 0) {
                groupMessagesRepository.save(new GroupMessages(" Message from User1", user, group));
            }else {
                groupMessagesRepository.save(new GroupMessages(" Message from User2", user2, group2));

            }
        }

        // when
        List<GroupMessages> groupMessagesOfUserGroups = groupMessagesRepository.getGroupMessagesOfUserGroups(user.getId());

        // then
        assertThat(groupMessagesOfUserGroups.size()).isEqualTo(8);
    }


    @Test
    void shouldGetNoGroupMessagesOfUserNotInGroups() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users userTemp2 = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(userTemp2);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(userTemp2.getUsername()).get();

        Groups groupTemp = new Groups("public", user);
        Groups group2Temp = new Groups("Test", user);

        groupsRepository.save(groupTemp);
        groupsRepository.save(group2Temp);

        Groups group = groupsRepository.findByNameSimple(groupTemp.getName());
        Groups group2 = groupsRepository.findByNameSimple(group2Temp.getName());

        for( int i = 0; i < 8; i++ ) {
            if(i%2 == 0) {
                groupMessagesRepository.save(new GroupMessages(" Message from User1", user, group));
            }else {
                groupMessagesRepository.save(new GroupMessages(" Message from User2", user2, group2));

            }
        }

        // when
        List<GroupMessages> groupMessagesOfUserGroups = groupMessagesRepository.getGroupMessagesOfUserGroups(user2.getId());

        // then
        assertThat(groupMessagesOfUserGroups.size()).isEqualTo(0);
    }


    @Test
    void shouldGetAllGroupMessages() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users userTemp2 = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(userTemp2);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(userTemp2.getUsername()).get();

        Groups groupTemp = new Groups("public", user);
        Groups group2Temp = new Groups("Test", user);

        groupsRepository.save(groupTemp);
        groupsRepository.save(group2Temp);

        Groups group = groupsRepository.findByNameSimple(groupTemp.getName());
        Groups group2 = groupsRepository.findByNameSimple(group2Temp.getName());

        for( int i = 0; i < 8; i++ ) {
            if(i%2 == 0) {
                groupMessagesRepository.save(new GroupMessages(" Message from User1", user, group));
            }else {
                groupMessagesRepository.save(new GroupMessages(" Message from User2", user2, group2));

            }
        }

        // when
        List<GroupMessages> groupMessagesOfUserGroups = groupMessagesRepository.getAllGroupMessages(group.getId()).get();

        // then
        assertThat(groupMessagesOfUserGroups.size()).isEqualTo(4);
    }


    @Test
    void shouldNotFindAnyGroupMessage() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users userTemp2 = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(userTemp2);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(userTemp2.getUsername()).get();

        Groups groupTemp = new Groups("public", user);
        Groups group2Temp = new Groups("Test", user);

        groupsRepository.save(groupTemp);
        groupsRepository.save(group2Temp);

        Groups group = groupsRepository.findByNameSimple(groupTemp.getName());
        Groups group2 = groupsRepository.findByNameSimple(group2Temp.getName());;

        for( int i = 0; i < 8; i++ ) {
            if(i%2 == 0) {
                groupMessagesRepository.save(new GroupMessages(" Message from User1", user, group));
            }else {
                groupMessagesRepository.save(new GroupMessages(" Message from User2", user, group));

            }
        }

        // when
        List<GroupMessages> groupMessagesOfUserGroups = groupMessagesRepository.getAllGroupMessages(group2.getId()).get();

        // then
        assertThat(groupMessagesOfUserGroups.size()).isEqualTo(0);
    }
}




























