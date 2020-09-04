package uk.co.datadisk.ddrolodex.domain.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {

    Authority authority;

    Role adminRole = Role.builder().name("ADMIN").build();
    Role managerRole = Role.builder().name("MANAGER").build();
    Role userRole = Role.builder().name("USER").build();
    Set<Role> roles = new HashSet<>(Set.of(adminRole, managerRole));

    @BeforeEach
    void setUp() {
        authority = Authority.builder().permission("user.create").roles(roles).build();
        adminRole.setAuthorities(new HashSet<>(Set.of(authority)));
    }

    @Test
    void getPermission() {
        assertEquals("user.create", authority.getPermission());
        System.out.println("Authority: " + authority);
    }

    @Test
    void setPermission() {
        authority.setPermission("user.update");
        assertNotEquals("user.create", authority.getPermission());
        assertEquals("user.update", authority.getPermission());
    }

    @Test
    void getRoles() {
        assertEquals(2, authority.getRoles().size());
        assertEquals(roles, authority.getRoles());
        assertTrue(authority.getRoles().equals(roles));
    }

    @Test
    void setRoles() {
        Set<Role> roles2 = new HashSet<>(Set.of(adminRole, managerRole, userRole));

        authority.setRoles(roles2);

        assertEquals(3, authority.getRoles().size());
        assertNotEquals(roles, authority.getRoles());
        assertTrue(authority.getRoles().equals(roles2));
    }
}