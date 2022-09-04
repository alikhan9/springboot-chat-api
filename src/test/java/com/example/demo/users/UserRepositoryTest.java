package com.example.demo.users;

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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application.properties"
)
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    private final Faker faker = new Faker();

    @Test
    void itShouldCheckWhenUserExistsByEmail() {
        // given
        Users user = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );
        userRepository.save(user);

        // when
        boolean expected = userRepository.existsByEmail(user.getEmail());

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenUserDoesNotExistByEmail() {
        // given
        String email = faker.internet().emailAddress();

        // when
        boolean expected = userRepository.existsByEmail(email);

        // then
        assertThat(expected).isFalse();
    }

    @Test
    void itShouldCheckWhenUserExistsByUsername() {
        // given
        Users user = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );
        userRepository.save(user);

        // when
        boolean expected = userRepository.existsByUsername(user.getUsername());

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenUserDoesNotExistByUsername() {
        // given
        String username = faker.name().username();

        // when
        boolean expected = userRepository.existsByUsername(username);

        // then
        assertThat(expected).isFalse();
    }

    @Test
    void ItShouldDeleteUserByUsername() {
        // given
        Users user = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );
        userRepository.save(user);

        // when
        Long expected = userRepository.deleteByUsername(user.getUsername());

        // then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    void ItShouldNotDeleteUserWhenUsernameIsNotFound() {
        // given
        String username = faker.name().username();

        // when
        Long expected = userRepository.deleteByUsername(username);

        // then
        assertThat(expected).isEqualTo(0);
    }

    @Test
    void ItShouldNotGetUserWhenUsernameIsNotFound() {
        // given
        String username = "john49";

        // when
        Optional<Users> expected = userRepository.findByUsername(username);

        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void ItShouldGetAllUserContacts() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users contact1Temp = new Users(
                "John",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        Users contact2Temp = new Users(
                "Johny",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        userRepository.save(userTemp);
        userRepository.save(contact1Temp);
        userRepository.save(contact2Temp);

        Optional<Users> user = userRepository.findByUsername(userTemp.getUsername());
        Optional<Users> contact1 = userRepository.findByUsername(contact1Temp.getUsername());
        Optional<Users> contact2 = userRepository.findByUsername(contact2Temp.getUsername());



        contactsRepository.save(new Contacts(user.get().getId(), contact1.get().getId()));
        contactsRepository.save(new Contacts(user.get().getId(), contact2.get().getId()));

        // when
        List<String> contacts = userRepository.getUserContacts(user.get().getId());

        // then
        assertThat(contacts).hasSize(2);
        assertThat(contacts).contains(contact1.get().getUsername());
        assertThat(contacts).contains(contact2.get().getUsername());
        assertThat(contacts).doesNotContain(user.get().getUsername());

    }

    @Test
    void ItShouldNotGetAnyUserContacts() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users contact1Temp = new Users(
                "John",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        Users contact2Temp = new Users(
                "Johny",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        userRepository.save(userTemp);
        userRepository.save(contact1Temp);
        userRepository.save(contact2Temp);

        Optional<Users> user = userRepository.findByUsername(userTemp.getUsername());
        Optional<Users> contact1 = userRepository.findByUsername(contact1Temp.getUsername());
        Optional<Users> contact2 = userRepository.findByUsername(contact2Temp.getUsername());



        contactsRepository.save(new Contacts(contact1.get().getId(), contact2.get().getId()));

        // when
        List<String> contacts = userRepository.getUserContacts(user.get().getId());

        // then
        assertThat(contacts.size()).isEqualTo(0);
    }

    @Test
    void ItShouldGetNotGetAnyone() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users contact1Temp = new Users(
                "John",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        Users contact2Temp = new Users(
                "Johny",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        userRepository.save(userTemp);
        userRepository.save(contact1Temp);
        userRepository.save(contact2Temp);

        Optional<Users> user = userRepository.findByUsername(userTemp.getUsername());
        Optional<Users> contact1 = userRepository.findByUsername(contact1Temp.getUsername());
        Optional<Users> contact2 = userRepository.findByUsername(contact2Temp.getUsername());



        contactsRepository.save(new Contacts(user.get().getId(), contact1.get().getId()));
        contactsRepository.save(new Contacts(user.get().getId(), contact2.get().getId()));

        // when
        List<String> usersUsername = userRepository.getAllUsersContactsByUsername(user.get().getId(), "Jo");

        // then
        assertThat(usersUsername.size()).isEqualTo(0);
    }


    @Test
    void ItShouldGetAnyoneWhoIsNotInContactListOfTheUser() {
        // given
        Users userTemp = new Users(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password()
        );

        Users contact1Temp = new Users(
                "John",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        Users contact2Temp = new Users(
                "Johny",
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                LocalDate.now(),
                faker.internet().password());

        userRepository.save(userTemp);
        userRepository.save(contact1Temp);
        userRepository.save(contact2Temp);

        Optional<Users> user = userRepository.findByUsername(userTemp.getUsername());
        Optional<Users> contact1 = userRepository.findByUsername(contact1Temp.getUsername());
        Optional<Users> contact2 = userRepository.findByUsername(contact2Temp.getUsername());

        contactsRepository.save(new Contacts( contact1.get().getId(), contact2.get().getId()));

        // when
        List<String> usersUsername = userRepository.getAllUsersContactsByUsername(user.get().getId(), "Jo");

        // then
        assertThat(usersUsername.size()).isEqualTo(2);
    }


}






























