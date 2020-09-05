package uk.co.datadisk.ddrolodex.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("home")
    public String showUser() {
        return "Hello World!";
    }

    //    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistException, EmailExistException {
//        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
//        return new ResponseEntity<>(newUser, OK);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestBody User user) {
//        // This will throw an exception if any issues authenticating
//        authenticate(user.getUsername(), user.getPassword());
//
//        User loginUser = userService.findUserByUsername(user.getUsername());
//        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
//
//        HttpHeaders jwtHeader = createJwtHeader(userPrincipal);
//
//        return new ResponseEntity<>(loginUser, jwtHeader, OK);
//    }
}
