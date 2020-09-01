package uk.co.datadisk.ddrolodex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddrolodex.domain.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
