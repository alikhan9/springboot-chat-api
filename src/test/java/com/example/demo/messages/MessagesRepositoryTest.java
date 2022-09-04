package com.example.demo.messages;

import com.example.demo.model.Contacts;
import com.example.demo.model.Messages;
import com.example.demo.model.Users;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.MessagesRepository;
import com.example.demo.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application.properties"
)
public class MessagesRepositoryTest {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    private final Faker faker = new Faker();


    @Test
    void isShouldGetAllMessagesBetweenTwoUsers() {
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

        for(Long i=1l; i<10; i++) {
            if(i%2==0)
                messagesRepository.save(new Messages(i, user, user2, "From user 1 to user 2", Timestamp.from(Instant.now())));
            else
                messagesRepository.save(new Messages(i, user2, user, "From user 2 to user 1", Timestamp.from(Instant.now())));
        }

        // when
        List<Messages> userMessagesFromOrToUser2 = messagesRepository.getUserMessagesFromOrToUser2(user.getId(), user2.getId());

        // then
        assertThat(userMessagesFromOrToUser2).hasSize(9);
        userMessagesFromOrToUser2.stream().map( message -> {
            return assertThat(message.getSender().getUsername()).satisfiesAnyOf(
                    (Consumer<? super String>) username -> assertThat(username).isEqualTo(user.getUsername()),
                    (Consumer<? super String>) username -> assertThat(username).isEqualTo(user2.getUsername())
            );
        });userMessagesFromOrToUser2.stream().map( message -> {
            return assertThat(message.getReceiver().getUsername()).satisfiesAnyOf(
                    (Consumer<? super String>) username -> assertThat(username).isEqualTo(user.getUsername()),
                    (Consumer<? super String>) username -> assertThat(username).isEqualTo(user2.getUsername())
            );
        });

    }

    @Test
    void isShouldGetNoMessages() {
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

        Users user3Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);
        userRepository.save(user3Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();
        Users user3 = userRepository.findByUsername(user3Temp.getUsername()).get();

        for(Long i=1l; i<10; i++) {
            if(i%2==0)
                messagesRepository.save(new Messages(i, user, user2, "From user 1 to user 2", Timestamp.from(Instant.now())));
            else
                messagesRepository.save(new Messages(i, user2, user, "From user 2 to user 1", Timestamp.from(Instant.now())));
        }

        // when
        List<Messages> userMessagesFromOrToUser = messagesRepository.getUserMessagesFromOrToUser2(user.getId(), user3.getId());
        List<Messages> userMessagesFromOrToUser2 = messagesRepository.getUserMessagesFromOrToUser2(user2.getId(), user3.getId());

        // then
        assertThat(userMessagesFromOrToUser).hasSize(0);
        assertThat(userMessagesFromOrToUser2).hasSize(0);
    }

    @Test
    void isShouldGetAllMessagesBetweenTheUserAndHisContacts() {
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

        Users user3Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);
        userRepository.save(user3Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();
        Users user3 = userRepository.findByUsername(user3Temp.getUsername()).get();

        contactsRepository.save(new Contacts(user.getId(), user2.getId()));
        contactsRepository.save(new Contacts(user.getId(), user3.getId()));


        for(Long i=1l; i<5; i++) {
            if(i%2==0)
                messagesRepository.save(new Messages(i, user, user2, "From user 1 to user 2", Timestamp.from(Instant.now())));
            else
                messagesRepository.save(new Messages(i, user2, user, "From user 2 to user 1", Timestamp.from(Instant.now())));
        }

        for(Long i=5l; i<9; i++) {
            if(i%2==0)
                messagesRepository.save(new Messages(i, user, user3, "From user 1 to user 3", Timestamp.from(Instant.now())));
            else
                messagesRepository.save(new Messages(i, user3, user, "From user 3 to user 1", Timestamp.from(Instant.now())));
        }

        // when
        List<Messages> userMessages= messagesRepository.getMessagesOfUserContacts(user.getId());

        // then
        assertThat(userMessages).hasSize(8);
    }

    @Test
    void isShouldGetNoMessagesBetweenTheUserAndHisContacts() {
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

        Users user3Temp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(user2Temp);
        userRepository.save(user3Temp);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(user2Temp.getUsername()).get();
        Users user3 = userRepository.findByUsername(user3Temp.getUsername()).get();

        for(Long i=1l; i<5; i++) {
            if(i%2==0)
                messagesRepository.save(new Messages(i, user, user2, "From user 1 to user 2", Timestamp.from(Instant.now())));
            else
                messagesRepository.save(new Messages(i, user2, user, "From user 2 to user 1", Timestamp.from(Instant.now())));
        }

        for(Long i=5l; i<9; i++) {
            if(i%2==0)
                messagesRepository.save(new Messages(i, user, user3, "From user 1 to user 3", Timestamp.from(Instant.now())));
            else
                messagesRepository.save(new Messages(i, user3, user, "From user 3 to user 1", Timestamp.from(Instant.now())));
        }

        // when
        List<Messages> userMessages= messagesRepository.getMessagesOfUserContacts(user.getId());

        // then
        assertThat(userMessages).hasSize(0);
    }



}
