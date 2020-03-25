package server.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import server.security.details.UserDetailImpl;

@Controller
public class RootController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String getRootPage(Authentication authentication){

        if(authentication != null){
            return "redirect:/home";
        } else {
            return "redirect:/signIn";
        }
    }
}
