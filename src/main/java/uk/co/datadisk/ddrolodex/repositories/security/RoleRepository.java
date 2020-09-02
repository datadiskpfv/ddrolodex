package uk.co.datadisk.ddrolodex.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddrolodex.domain.security.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String role);
}
