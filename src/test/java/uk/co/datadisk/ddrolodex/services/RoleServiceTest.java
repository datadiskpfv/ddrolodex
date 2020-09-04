package uk.co.datadisk.ddrolodex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.repositories.security.RoleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoleServiceTest {

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    List<Authority> adminAuth;
    List<Authority> managerAuth;

    Authority authority1;
    Authority authority2;

    Role adminRole;
    Role managerRole;

    @BeforeEach
    void setUp() {
        authority1 = Authority.builder().permission("user.create").build();
        authority2 = Authority.builder().permission("user.delete").build();

        adminAuth = new ArrayList<>(Arrays.asList(authority1, authority2));
        managerAuth = new ArrayList<>(Arrays.asList(authority1));

        adminRole = Role.builder().name("ADMIN").authorities(adminAuth).build();
        managerRole = Role.builder().name("MANAGER").authorities(managerAuth).build();
    }

    @Test
    void findRoleByNameAdmin() {
        when(roleRepository.findRoleByName("ADMIN")).thenReturn(Optional.ofNullable(adminRole));

        assertEquals("ADMIN", roleService.findRoleByName("ADMIN").getName());
        assertTrue(roleService.findRoleByName("ADMIN").getAuthorities().containsAll(Arrays.asList(authority1, authority2)));

        verify(roleRepository, times(2)).findRoleByName(anyString());
    }

    @Test
    void findRoleByNameManager() {
        when(roleRepository.findRoleByName("MANAGER")).thenReturn(Optional.ofNullable(managerRole));

        assertEquals("MANAGER", roleService.findRoleByName("MANAGER").getName());
        assertTrue(roleService.findRoleByName("MANAGER").getAuthorities().containsAll(Arrays.asList(authority1)));
        assertFalse(roleService.findRoleByName("MANAGER").getAuthorities().containsAll(Arrays.asList(authority2)));

        verify(roleRepository, times(3)).findRoleByName(anyString());
    }
}