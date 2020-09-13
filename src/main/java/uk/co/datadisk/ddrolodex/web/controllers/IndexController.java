package uk.co.datadisk.ddrolodex.web.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.exceptions.domain.EmailExistException;
import uk.co.datadisk.ddrolodex.exceptions.domain.RoleNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UserNotFoundException;
import uk.co.datadisk.ddrolodex.exceptions.domain.UsernameExistException;
import uk.co.datadisk.ddrolodex.jwt.JWTTokenProvider;
import uk.co.datadisk.ddrolodex.services.UserService;

import static uk.co.datadisk.ddrolodex.constants.SecurityConstant.JWT_TOKEN_HEADER;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("register")
    public String RegisterUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
    @RequestParam("username") String username, @RequestParam("email") String email)
        throws UserNotFoundException, UsernameExistException, EmailExistException, RoleNotFoundException {
        userService.register(firstName, lastName, username, email);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
