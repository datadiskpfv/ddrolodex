package uk.co.datadisk.ddrolodex.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/user"})
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @GetMapping("/user")
    public String users() {
        return "/user/user";
    }
}
