package uk.co.datadisk.ddrolodex.domain.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    Role adminRole;
    Set<Role> roles;

    Authority userCreate;
    Authority userRead;
    Authority userUpdate;
    Authority userDelete;


    @BeforeEach
    void setUp() {
        adminRole = Role.builder().name("ADMIN").build();
        roles  = new HashSet<>(Set.of(adminRole));

        userCreate = Authority.builder().permission("user.create").roles(roles).build();
        userRead = Authority.builder().permission("user.create").roles(roles).build();
        userUpdate = Authority.builder().permission("user.create").roles(roles).build();
        userDelete = Authority.builder().permission("user.create").roles(roles).build();

        adminRole.setAuthorities(Set.of(userCreate, userRead, userUpdate, userDelete));
    }

    @Test
    void getName() {
        assertEquals("ADMIN", adminRole.getName());
    }

    @Test
    void getAuthorities() {
        assertEquals(4, adminRole.getAuthorities().size());
        assertTrue(adminRole.getAuthorities().containsAll(Arrays.asList(userCreate, userRead, userUpdate, userDelete)));
    }

    @Test
    void setName() {
        adminRole.setName("ADMINISTRATION");
        assertNotEquals("ADMIN", adminRole.getName());
        assertEquals("ADMINISTRATION", adminRole.getName());
    }

    @Test
    void setAuthorities() {
        adminRole.setAuthorities(Set.of(userCreate, userRead));
        assertTrue(adminRole.getAuthorities().containsAll(Arrays.asList(userCreate, userRead)));
        assertFalse(adminRole.getAuthorities().containsAll(Arrays.asList(userCreate, userRead, userUpdate, userDelete)));
    }
}