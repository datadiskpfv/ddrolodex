package uk.co.datadisk.ddrolodex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.repositories.security.AuthorityRepository;
import uk.co.datadisk.ddrolodex.services.impl.AuthorityServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
//@WebMvcTest
class AuthorityServiceTest {

    @MockBean
    AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    List<Authority> authorities;

    @BeforeEach
    void setUp() {
        authorities = new ArrayList<>(Arrays.asList(Authority.builder().permission("user.create").build()));
    }

    @Test
    void createAuthority() {
    }

    @Test
    void testCreateAuthority() {
    }

    @Test
    void findAuthorityByName() {
        Authority authority = Authority.builder().permission("user.create").build();
        when(authorityRepository.findAuthorityByPermission("user.create")).thenReturn(Optional.ofNullable((authority)));

        assertEquals("user.create", authorityService.findAuthorityByName("user.create").getPermission());
        verify(authorityRepository, times(1)).findAuthorityByPermission("user.create");
    }

    @Test
    void findAll() {
        when(authorityRepository.findAll()).thenReturn((authorities));
        assertEquals(1, authorityService.findAll().size());

        verify(authorityRepository, times(1)).findAll();
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

//    @Test
//    void findAuthorityByName() {
//    }
//
//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void update() {

//    }
//
//    @Test
//    void delete() {
//    }
}