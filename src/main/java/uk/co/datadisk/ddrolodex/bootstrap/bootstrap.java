package uk.co.datadisk.ddrolodex.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.repositories.security.AuthorityRepository;
import uk.co.datadisk.ddrolodex.repositories.security.RoleRepository;
import uk.co.datadisk.ddrolodex.repositories.security.UserRepository;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class bootstrap implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String... args) {
        LOGGER.info("Loading data via bootstrap");
        LOGGER.info("==========================");
        //loadSecurityData();
        //loadUserData();
    }

    public void loadSecurityData() {

        LOGGER.info("Loading Authorities");

        // User Authorities
        Authority userCreate = authorityRepository.save(Authority.builder().permission("user.create").build());
        Authority userRead = authorityRepository.save(Authority.builder().permission("user.read").build());
        Authority userUpdate = authorityRepository.save(Authority.builder().permission("user.update").build());
        Authority userDelete = authorityRepository.save(Authority.builder().permission("user.delete").build());

        // Contact Authorities
        Authority contactCreate = authorityRepository.save(Authority.builder().permission("contact.create").build());
        Authority contactRead = authorityRepository.save(Authority.builder().permission("contact.read").build());
        Authority contactUpdate = authorityRepository.save(Authority.builder().permission("contact.update").build());
        Authority contactDelete = authorityRepository.save(Authority.builder().permission("contact.delete").build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role managerRole = roleRepository.save(Role.builder().name("MANAGER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        adminRole.setAuthorities(new HashSet<>(Set.of(userCreate, userRead, userUpdate, userDelete)));
        managerRole.setAuthorities(new HashSet<>(Set.of(userCreate, userRead, userUpdate, contactRead)));
        userRole.setAuthorities(new HashSet<>(Set.of(contactCreate, contactRead, contactUpdate, contactDelete)));

        roleRepository.saveAll(Arrays.asList(adminRole, managerRole, userRole));
    }

    public void loadUserData() {

        LOGGER.info("Loading User Data");

        // create an admin user
        Role adminRole = roleRepository.findRoleByName("ADMIN").orElseThrow();
        Role managerRole = roleRepository.findRoleByName("MANAGER").orElseThrow();
        Role userRole = roleRepository.findRoleByName("USER").orElseThrow();

        LOGGER.info("Role: " + adminRole.getName());

        String password = passwordEncoder.encode("password");

        User admin1 = userRepository.save(User.builder()
                .firstName("Paul")
                .lastName("Valle")
                .username("pvalle")
                .email("paul.valle@datadisk.co.uk")
                .password(password)
                .lastLoginDate(new Date())
                .lastLoginDateDisplay(new Date())
                .joinDate(new Date())
                .role(adminRole)
                .build());

        User manager1 = userRepository.save(User.builder()
                .firstName("Manager")
                .lastName("Manager")
                .username("manager")
                .email("manager@datadisk.co.uk")
                .password(password)
                .lastLoginDate(new Date())
                .lastLoginDateDisplay(new Date())
                .joinDate(new Date())
                .role(managerRole)
                .build());

        User user1 = userRepository.save(User.builder()
                .firstName("user")
                .lastName("user")
                .username("user")
                .email("user@datadisk.co.uk")
                .password(password)
                .lastLoginDate(new Date())
                .lastLoginDateDisplay(new Date())
                .joinDate(new Date())
                .role(userRole)
                .build());

        LOGGER.info("Created: " + admin1.getUsername());
        LOGGER.info("Created: " + manager1.getUsername());
        LOGGER.info("Created: " + user1.getUsername());
    }
}