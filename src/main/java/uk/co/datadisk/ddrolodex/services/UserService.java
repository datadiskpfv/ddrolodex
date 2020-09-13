package uk.co.datadisk.ddrolodex.services;

import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.domain.*;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    List<User> getUsers();

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);

    User updateUser(String currentUsername, String firstName, String lastName, String username, String email,
                String role, Boolean isActive, Boolean isNonLocked) throws UsernameExistException, EmailExistException, UserNotFoundException;

    void deleteById(Long id);

    User register(String firstName, String lastName, String username, String email) throws RoleNotFoundException, UsernameExistException, EmailExistException, UserNotFoundException;

    void resetPassword(String email) throws EmailNotFoundException;

    void deleteUser(String username) throws EmailNotFoundException;
}
