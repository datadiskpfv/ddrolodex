package uk.co.datadisk.ddrolodex.services;

import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.domain.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);

    User update(User user);

    void deleteById(Long id);
}
