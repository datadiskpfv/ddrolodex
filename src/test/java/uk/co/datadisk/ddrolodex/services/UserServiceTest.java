package uk.co.datadisk.ddrolodex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.repositories.security.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    List<Authority> adminAuth;
    List<Authority> managerAuth;

    Role adminRole;
    Role managerRole;

    User admin;
    User manager;

    @BeforeEach
    void setUp() {
        Authority authority1 = Authority.builder().permission("user.create").build();
        Authority authority2 = Authority.builder().permission("user.delete").build();

        adminAuth = new ArrayList<>(Arrays.asList(authority1, authority2));
        managerAuth = new ArrayList<>(Arrays.asList(authority1));

        adminRole = Role.builder().name("ADMIN").authorities(adminAuth).build();
        managerRole = Role.builder().name("MANAGER").authorities(managerAuth).build();

        admin = User.builder()
                .firstName("Paul")
                .lastName("Valle")
                .username("pvalle")
                .email("paul.valle@datadisk.co.uk")
                .password("password")
                .lastLoginDate(new Date())
                .lastLoginDateDisplay(new Date())
                .joinDate(new Date())
                .role(adminRole)
                .build();

        manager = User.builder()
                .firstName("Manager")
                .lastName("Manager")
                .username("manager")
                .email("manager@datadisk.co.uk")
                .password("password")
                .lastLoginDate(new Date())
                .lastLoginDateDisplay(new Date())
                .joinDate(new Date())
                .role(managerRole)
                .build();
    }

    @Test
    void create() {
        System.out.println("Debug");
        when(userRepository.save(admin)).thenReturn(admin);
        assertEquals("pvalle", userService.create(admin).getUsername());
        verify(userRepository, times(1)).save(admin);
    }

    @ParameterizedTest
    @ValueSource(strings = {"pvalle", "manager"})
    void findUserByUsername(String username) {

        User user;

        if(username.equals("pvalle")) {
            user = admin;
        } else {
            user = manager;
        }

        when(userRepository.findUserByUsername(username)).thenReturn(java.util.Optional.ofNullable(user));
        assertEquals(username, userService.findUserByUsername(username).get().getUsername());
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"paul.valle@datadisk.co.uk", "manager@datadisk.co.uk"})
    void findUserByEmail(String email) {

        User user;

        if(email.equals("paul.valle@datadisk.co.uk")) {
            user = admin;
        } else {
            user = manager;
        }

        when(userRepository.findUserByEmail(email)).thenReturn(java.util.Optional.ofNullable(user));
        assertEquals(email, userService.findUserByEmail(email).get().getEmail());
        verify(userRepository, times(1)).findUserByEmail(anyString());
    }

//    @Test
//    void update() {
//        User updatedAdmin = User.builder()
//                .firstName("Paul")
//                .lastName("Valle")
//                .username("pvalle")
//                .email("paul.valle1@datadisk.co.uk")
//                .password("password1")
//                .lastLoginDate(new Date())
//                .lastLoginDateDisplay(new Date())
//                .joinDate(new Date())
//                .role(adminRole)
//                .build();
//
//        when(userRepository.save(admin)).thenReturn(updatedAdmin);
//        assertEquals(updatedAdmin, userService.update(admin));
//        verify(userRepository, times(1)).save(admin);
//    }

    @Test
    void deleteById() {
        Long id = 1L;
        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}