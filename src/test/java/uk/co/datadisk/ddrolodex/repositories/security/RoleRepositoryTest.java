package uk.co.datadisk.ddrolodex.repositories.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.co.datadisk.ddrolodex.domain.security.Role;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)     // because i am using MySQL not embedded
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findRoleByName() {
        Optional<Role> role = roleRepository.findRoleByName("ADMIN");
        assertTrue(role.isPresent());
        assertEquals("ADMIN", role.get().getName());
    }

    @Test
    void finalAllRoles() {
        assertEquals(3, roleRepository.findAll().size());
        List<Role> roles = roleRepository.findAll();
        assertTrue(roles.stream().anyMatch(o -> o.getName().equals("ADMIN")));
        assertTrue(roles.stream().anyMatch(o -> o.getName().equals("MANAGER")));
        assertTrue(roles.stream().anyMatch(o -> o.getName().equals("USER")));
    }
}