package uk.co.datadisk.ddrolodex.domain.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserTest {

    User adminUser;
    User managerUser;

    Role adminRole;
    Set<Role> adminRoles;
    Role managerRole;
    Set<Role> managerRoles;

    Authority userCreate;
    Authority userDelete;

    Set<GrantedAuthority> adminGrantedAuthorities;
    Set<GrantedAuthority> managerGrantedAuthorities;


    @BeforeEach
    void setUp() {
        adminRole = Role.builder().name("ADMIN").build();
        managerRole = Role.builder().name("MANAGER").build();
        adminRoles  = new HashSet<>(Set.of(adminRole));
        managerRoles  = new HashSet<>(Set.of(managerRole));

        userCreate = Authority.builder().permission("user.create").roles(Set.of(adminRole, managerRole)).build();
        userDelete = Authority.builder().permission("user.delete").roles(Set.of(adminRole)).build();

        adminRole.setAuthorities(Set.of(userCreate, userDelete));
        managerRole.setAuthorities(Set.of(userCreate));

        adminUser = User.builder().username("pvalle").email("paul.valle@datadisk.co.uk").role(adminRole).build();
        managerUser = User.builder().username("manager").email("manager@datadisk.co.uk").role(managerRole).build();

        adminGrantedAuthorities = new HashSet<>(Arrays.asList(
                new SimpleGrantedAuthority("user.create"),
                new SimpleGrantedAuthority("user.delete")
        ));

        managerGrantedAuthorities = new HashSet<>(Arrays.asList(
                new SimpleGrantedAuthority("user.create")
        ));
    }

    @Test
    void getAuthorities() {
        assertEquals(2, adminUser.getAuthorities().size());
        assertEquals(1, managerUser.getAuthorities().size());
    }

    @Test
    void getUsername() {
        System.out.println("test");
        assertEquals("pvalle", adminUser.getUsername());
        assertEquals("manager", managerUser.getUsername());
    }

    @Test
    void getEmail() {
        assertEquals("paul.valle@datadisk.co.uk", adminUser.getEmail());
        assertEquals("manager@datadisk.co.uk", managerUser.getEmail());
    }

    @Test
    void getRole() {
        assertEquals("ADMIN", adminUser.getRole().getName());
        assertEquals("MANAGER", managerUser.getRole().getName());
    }

    @Test
    void setUsername() {
        adminUser.setUsername("pvalle1");
        managerUser.setUsername("manager1");

        assertNotEquals("pvalle", adminUser.getUsername());
        assertNotEquals("manager", managerUser.getUsername());

        assertEquals("pvalle1", adminUser.getUsername());
        assertEquals("manager1", managerUser.getUsername());
    }

    @Test
    void setEmail() {
        adminUser.setEmail("paul.valle1@datadisk.co.uk");
        managerUser.setEmail("manager1@datadisk.co.uk");

        assertNotEquals("paul.valle@datadisk.co.uk", adminUser.getEmail());
        assertNotEquals("manager@datadisk.co.uk", managerUser.getEmail());

        assertEquals("paul.valle1@datadisk.co.uk", adminUser.getEmail());
        assertEquals("manager1@datadisk.co.uk", managerUser.getEmail());
    }

    @Test
    void setRole() {
        adminUser.setRole(managerRole);
        managerUser.setRole(adminRole);

        assertNotEquals("ADMIN", adminUser.getRole().getName());
        assertNotEquals("MANAGER", managerUser.getRole().getName());

        assertEquals("MANAGER", adminUser.getRole().getName());
        assertEquals("ADMIN", managerUser.getRole().getName());
    }

    @Test
    void adminPermissions() {
        assertTrue(adminUser.getAuthorities().equals(adminGrantedAuthorities));
        assertFalse(adminUser.getAuthorities().equals(managerGrantedAuthorities));
    }

    @Test
    void managerPermissions() {
        assertTrue(managerUser.getAuthorities().equals(managerGrantedAuthorities));
        assertFalse(managerUser.getAuthorities().equals(adminGrantedAuthorities));
    }
}