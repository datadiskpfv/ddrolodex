package uk.co.datadisk.ddrolodex.repositories.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.co.datadisk.ddrolodex.domain.security.Authority;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)     // because i am using MySQL not embedded
class AuthorityRepositoryTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    void findAuthorityByPermission() {
        Optional<Authority> userCreate = authorityRepository.findAuthorityByPermission("user.create");

        assertTrue(userCreate.isPresent());
        assertEquals("user.create", userCreate.get().getPermission());
    }

    @Test
    void findAllAuthorities() {
        List<Authority> authorities = authorityRepository.findAll();
        assertEquals(8, authorities.size());
    }
}