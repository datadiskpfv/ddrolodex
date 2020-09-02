package uk.co.datadisk.ddrolodex.services;

import uk.co.datadisk.ddrolodex.domain.security.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {

    // CRUD methods
    Authority createAuthority(String permission);

    Authority findAuthorityByName(String permission);
    List<Authority> findAll();

    Authority update(Authority authority);

    void delete(Long id);
}
