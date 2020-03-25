package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.business_logic.services.UserService;

@Controller
public class ConfirmController {

    @Autowired
    private UserService userService;

    @GetMapping("/confirm")
    public String confirm(@RequestParam("t") String token,
                          @RequestParam("u") String name,
                          @RequestParam("m") String mail){

        if(userService.confirmUser(name, mail, token)){
            return "redirect:/signIn";
        }else {
            return "redirect:/signUp";
        }
    }
}
