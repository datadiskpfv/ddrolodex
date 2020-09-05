package uk.co.datadisk.ddrolodex.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailExistException;
import uk.co.datadisk.ddrolodex.exceptions.domain.RoleNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UsernameExistException;
import uk.co.datadisk.ddrolodex.repositories.security.RoleRepository;
import uk.co.datadisk.ddrolodex.repositories.security.UserRepository;
import uk.co.datadisk.ddrolodex.services.UserService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static uk.co.datadisk.ddrolodex.constants.UserImplConstant.*;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User register(String firstName, String lastName, String username, String email)
            throws UsernameExistException, EmailExistException, RoleNotFoundException {

        Role userRole = roleRepository.findRoleByName("USER").orElseThrow( () -> new RoleNotFoundException(NO_ROLE_FOUND));

        validateNewUsernameAndEmail(EMPTY, username, email);

        String password = generatePassword();

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .joinDate(new Date())
                .isActive(true)
                .isNotLocked(true)
                .role(userRole)
                .build();

        userRepository.save(user);

        // commented out as already tested and dont want to keep sending emails to myself
//        try {
//            emailService.sendNewPasswordEmail(firstName, password, email);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        log.info("Sending email to Username: " + username + "  Password: " + password);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UsernameNotFoundException, UsernameExistException, EmailExistException {

        User userByNewUsername = findUserByUsername(newUsername).orElse(null);
        User userByNewEmail = findUserByEmail(newEmail).orElse(null);

        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername).orElse(null);

            // confirm that the user has a current username
            if(currentUser == null) {
                throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }

            // Confirm new username does not exist
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }

            // confirm that email does not exist
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }

            if(userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
