package uk.co.datadisk.ddrolodex.services;

import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailExistException;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.RoleNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UsernameExistException;

import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);

    User update(User user);

    void deleteById(Long id);

    User register(String firstName, String lastName, String username, String email) throws RoleNotFoundException, UsernameExistException, EmailExistException;

    void resetPassword(String email) throws EmailNotFoundException;
}
