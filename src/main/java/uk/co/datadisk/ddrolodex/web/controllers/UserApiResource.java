package uk.co.datadisk.ddrolodex.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.co.datadisk.ddrolodex.domain.HttpResponse;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.GlobalExceptionHandler;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailExistException;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UserNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UsernameExistException;
import uk.co.datadisk.ddrolodex.services.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAuthority('USER')")
public class UserApiResource extends GlobalExceptionHandler {

    public static final String EMAIL_SENT  = "An email with a new password was sent to ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

    private UserService userService;

    @Autowired
    public UserApiResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam("currentUsername") String currentUsername,
                                        @RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("username") String username,
                                        @RequestParam("email") String email,
                                        @RequestParam("role") String role,
                                        @RequestParam("isActive") String isActive,
                                        @RequestParam("isNonLocked") String isNonLocked) throws UsernameExistException, EmailExistException, UserNotFoundException {

        System.out.println("User APi Controller");
        User updatedUser = userService.updateUser(currentUsername, firstName, lastName, username, email,
                role, Boolean.parseBoolean(isActive), Boolean.parseBoolean(isNonLocked));

        return new ResponseEntity<>(updatedUser, OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> findUser(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(null, NO_CONTENT);
        }
        System.out.println("User found: " + user.getUsername());
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<HttpResponse> getAllUsers(@PathVariable("email") String email) throws EmailNotFoundException {
        System.out.println("Resetting password for " + email);
        userService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }

//    @DeleteMapping("/delete/{username}")
//    @PreAuthorize("hasAnyAuthority('user:delete')")
//    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
//        userService.deleteUser(username);
//        return response(OK, USER_DELETED_SUCCESSFULLY);
//    }


    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

//    private HttpHeaders createJwtHeader(UserPrincipal userPrincipal) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
//        return headers;
//    }

//    private void authenticate(String username, String password) {
//        // This will throw an exception if any issues authenticating
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//    }
}
