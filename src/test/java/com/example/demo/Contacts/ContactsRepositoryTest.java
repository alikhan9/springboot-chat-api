package com.example.demo.Contacts;

import com.example.demo.model.Contacts;
import com.example.demo.model.Users;
import com.example.demo.repository.ContactsRepository;
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
public class ContactsRepositoryTest {

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private UserRepository userRepository;

    private final Faker faker = new Faker();


    @Test
    void canGetAllUserContacts() {
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

        Users userTemp3 = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        userRepository.save(userTemp);
        userRepository.save(userTemp2);
        userRepository.save(userTemp3);

        Users user = userRepository.findByUsername(userTemp.getUsername()).get();
        Users user2 = userRepository.findByUsername(userTemp2.getUsername()).get();
        Users user3 = userRepository.findByUsername(userTemp3.getUsername()).get();

        contactsRepository.save(new Contacts(user.getId(), user2.getId()));
        contactsRepository.save(new Contacts(user.getId(), user3.getId()));

        // when
        List<Contacts> allUserContacts = contactsRepository.getAllUserContacts(user.getId());

        // then
        assertThat(allUserContacts).hasSize(2);
    }


    @Test
    void canDeleteContact() {
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

        contactsRepository.save(new Contacts(user.getId(), user2.getId()));

        // when
        int i = contactsRepository.deleteContact(user.getId(), user2.getId());
        List<Contacts> allUserContacts = contactsRepository.getAllUserContacts(user.getId());

        // then
        assertThat(i).isEqualTo(1);
        assertThat(allUserContacts.size()).isEqualTo(0);
    }

}
