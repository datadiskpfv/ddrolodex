package uk.co.datadisk.ddrolodex.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.domain.*;
import uk.co.datadisk.ddrolodex.repositories.security.RoleRepository;
import uk.co.datadisk.ddrolodex.repositories.security.UserRepository;
import uk.co.datadisk.ddrolodex.services.UserService;

import java.util.Date;
import java.util.List;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User updateUser(String currentUsername, String firstName, String lastName, String newUsername, String email, String role, Boolean isActive, Boolean isNonLocked) throws UsernameExistException, EmailExistException, UserNotFoundException {
        validateNewUsernameAndEmail(currentUsername, newUsername, email);

        User user = findUserByUsername(currentUsername).orElse(null);
        Role userRole = roleRepository.findRoleByName(role).get();

        if (user == null || userRole == null ) {
            return null;
        }
        user.setUsername(newUsername);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(userRole);
        user.setActive(isActive);
        user.setAccountNonLocked(isNonLocked);

        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User register(String firstName, String lastName, String username, String email)
            throws UsernameExistException, EmailExistException, RoleNotFoundException, UserNotFoundException {

        Role userRole = roleRepository.findRoleByName("USER").orElseThrow( () -> new RoleNotFoundException(NO_ROLE_FOUND));

        validateNewUsernameAndEmail(EMPTY, username, email);

        String password = generatePassword();

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElse(null);

        if (user == null) {
            log.error(USER_NOT_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(USER_NOT_FOUND_BY_USERNAME + username);
        } else {

            //validateLoginAttempt(user);

            // update login dates and save user in db
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            user.setPassword(user.getPassword());
            userRepository.save(user);

            log.info(RETURNING_FOUND_USER_BY_USERNAME + username);

            return user;
        }
    }

    public User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UsernameExistException, EmailExistException, UserNotFoundException {

        User userByNewUsername = findUserByUsername(newUsername).orElse(null);
        User userByNewEmail = findUserByEmail(newEmail).orElse(null);

        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername).orElse(null);

            // confirm that the user has a current username
            if(currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
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

    @Override
    public void resetPassword(String email) throws EmailNotFoundException {
        User user = userRepository.findUserByEmail(email).orElse(null);

        if (user ==  null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

        log.info("Sending email to Username: " + email + "  Password: " + password);

        //emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
    }

//    private void validateLoginAttempt(User user) {
//        if(user.isNotLocked()) {
//            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
//                user.setNotLocked(false);
//            } else {
//                user.setNotLocked(true);
//            }
//        } else {
//            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
//        }
//    }

    private String generatePassword() {
        return "password";
        //return RandomStringUtils.randomAlphanumeric(10);
    }
}
