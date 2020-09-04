package uk.co.datadisk.ddrolodex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityServiceTest {

    @Autowired
    private AuthorityService authorityService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createAuthority() {
        String permission = "user.create";
        authorityService.createAuthority(permission);
    }

    @Test
    void testCreateAuthority() {
    }

    @Test
    void findAuthorityByName() {
    }

    @Test
    void findAll() {
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