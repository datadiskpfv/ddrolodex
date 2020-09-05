package uk.co.datadisk.ddrolodex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.repositories.security.AuthorityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorityServiceTest {

    @MockBean
    AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    List<Authority> authorities;
    Authority authority;
    Authority authority2;

    @BeforeEach
    void setUp() {
        authorities = new ArrayList<>(Arrays.asList(Authority.builder().permission("user.create").build()));
        authority = Authority.builder().permission("user.create").build();
        authority2 = Authority.builder().permission("user.read").build();
    }

    @Test
    void createAuthority() {
        when(authorityRepository.save(any(Authority.class))).thenReturn(authority);

        assertEquals("user.create", authorityService.createAuthority("user.create").getPermission());
        verify(authorityRepository, times(1)).save(any(Authority.class));

    }

    @Test
    void findAuthorityByName() {
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
        when(authorityRepository.save(authority2)).thenReturn(authority);

        assertEquals("user.read", authorityService.update(authority2).getPermission());
        verify(authorityRepository, times(1)).save(authority2);
    }

    @Test
    void delete() {
        Long id = 1L;
        authorityService.delete(id);
        verify(authorityRepository, times(1)).deleteById(anyLong());
    }
}