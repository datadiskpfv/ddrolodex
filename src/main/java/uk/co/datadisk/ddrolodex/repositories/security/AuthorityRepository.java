package uk.co.datadisk.ddrolodex.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddrolodex.domain.security.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findAuthorityByPermission(String permission);
}
