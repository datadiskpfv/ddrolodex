package uk.co.datadisk.ddrolodex.web.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailExistException;
import uk.co.datadisk.ddrolodex.exceptions.domain.RoleNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UserNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UsernameExistException;
import uk.co.datadisk.ddrolodex.jwt.JWTTokenProvider;
import uk.co.datadisk.ddrolodex.repositories.security.UserRepository;
import uk.co.datadisk.ddrolodex.services.UserService;

import static org.springframework.http.HttpStatus.OK;
import static uk.co.datadisk.ddrolodex.constants.SecurityConstant.JWT_TOKEN_HEADER;
import static uk.co.datadisk.ddrolodex.constants.UserImplConstant.NO_USER_FOUND;

@RestController
@RequestMapping(path = {"/api"})
public class IndexApiController {

    private UserService userService;
    private JWTTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    public IndexApiController(UserService userService, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @GetMapping("home")
    public String showUser() {
        return "Hello World!";
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistException, EmailExistException, RoleNotFoundException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User userLogin) throws UserNotFoundException {
        // This will throw an exception if any issues authenticating
        authenticate(userLogin.getUsername(), userLogin.getPassword());

        User user = userService.findUserByUsername(userLogin.getUsername()).orElseThrow(() -> new UserNotFoundException(NO_USER_FOUND));

        HttpHeaders jwtHeader = createJwtHeader(user);

        return new ResponseEntity<>(user, jwtHeader, OK);
    }

    private HttpHeaders createJwtHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        // This will throw an exception if any issues authenticating
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
