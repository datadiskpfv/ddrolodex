package uk.co.datadisk.ddrolodex.repositories.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)     // because i am using MySQL not embedded
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findUserByUsername() {
        assertEquals("pvalle", userRepository.findUserByUsername("pvalle").get().getUsername());
        assertEquals("manager", userRepository.findUserByUsername("manager").get().getUsername());
        assertEquals("user", userRepository.findUserByUsername("user").get().getUsername());
    }

    @Test
    void findUserByEmail() {
        assertEquals("pvalle", userRepository.findUserByEmail("paul.valle@datadisk.co.uk").get().getUsername());
        assertEquals("manager", userRepository.findUserByEmail("manager@datadisk.co.uk").get().getUsername());
        assertEquals("user", userRepository.findUserByEmail("user@datadisk.co.uk").get().getUsername());
    }
}