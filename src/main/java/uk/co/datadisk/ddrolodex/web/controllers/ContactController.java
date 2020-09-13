package uk.co.datadisk.ddrolodex.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/contact"})
@PreAuthorize("hasAuthority('USER')")
public class ContactController {

    @GetMapping("contact")
    public String contacts() {
        return "/contact/contact";
    }
}
