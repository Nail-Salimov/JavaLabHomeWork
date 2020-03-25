package server.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import server.entities.model.UserDataModel;
import server.security.details.UserDetailImpl;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/home")
    public String getPage(Authentication authentication, Model model){
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        UserDataModel userDataModel = userDetail.getUser();
        model.addAttribute("user", userDataModel);
        return "home";
    }
}
