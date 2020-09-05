package uk.co.datadisk.ddrolodex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void create() {
        when(roleRepository.save(adminRole)).thenReturn(adminRole);
        assertEquals("ADMIN", roleService.create(adminRole).getName());
        verify(roleRepository, times(1)).save(adminRole);
    }

    // Below are two tests that use different ways to pass parameters

    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "MANAGER"})
    void findRoleByName(String username) {

        Role role;
        List<Authority> authorityList;

        if(username.equals("ADMIN")) {
            role = adminRole;
            authorityList = adminAuth;
        } else {
            role = managerRole;
            authorityList = managerAuth;
        }

        when(roleRepository.findRoleByName(username)).thenReturn(Optional.ofNullable(role));

        assertEquals(username, roleService.findRoleByName(username).getName());
        assertTrue(roleService.findRoleByName(username).getAuthorities().containsAll(authorityList));

        verify(roleRepository, times(2)).findRoleByName(anyString());
    }

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("uk.co.datadisk.ddrolodex.services.ServiceData#getStreamFindUsers")
    void findRole(String username, Role role, List<Authority> authorityList) {

        when(roleRepository.findRoleByName(username)).thenReturn(Optional.ofNullable(role));

        assertEquals(username, roleService.findRoleByName(username).getName());
        assertTrue(roleService.findRoleByName(username).getAuthorities().containsAll(authorityList));

        verify(roleRepository, times(2)).findRoleByName(anyString());
    }

    @Test
    void update() {
        when(roleRepository.save(adminRole)).thenReturn(adminRole);
        assertEquals("ADMIN", roleService.update(adminRole).getName());
        verify(roleRepository, times(1)).save(adminRole);
    }

    @Test
    void delete() {
        Long id = 1L;
        roleService.deleteById(id);
        verify(roleRepository, times(1)).deleteById(anyLong());
    }
}