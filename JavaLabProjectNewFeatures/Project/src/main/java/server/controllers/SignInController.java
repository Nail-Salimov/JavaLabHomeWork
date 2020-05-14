package server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import server.entities.user.dto.SignUpForm;

@Controller
public class SignInController {

    @GetMapping("/signIn")
    public String getPage(Model model){
        model.addAttribute("profileForm", new SignUpForm());
        return "signIn";
    }

}
