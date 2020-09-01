package uk.co.datadisk.ddrolodex.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.repositories.AuthorityRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class bootstrap implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Loading data via bootstrap");
        LOGGER.info("==========================");
        loadSecurityData();
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
    }
}