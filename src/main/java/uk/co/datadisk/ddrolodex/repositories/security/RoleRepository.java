package uk.co.datadisk.ddrolodex.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddrolodex.domain.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
