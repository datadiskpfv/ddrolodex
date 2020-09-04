package uk.co.datadisk.ddrolodex.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddrolodex.domain.security.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
