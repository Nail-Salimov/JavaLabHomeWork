package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import server.bl.services.UserService;
import server.entities.user.dto.SignUpForm;
import server.entities.user.dto.UserDataDto;

import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public String getPage(Authentication authentication, Model model) {
        if (authentication != null) {
            return "redirect:/store";
        } else {
            model.addAttribute("signUpForm", new SignUpForm());
            return "signUp";
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public String updatePage(@Valid SignUpForm form,
                           BindingResult bindingResult, Model model){

        if (!bindingResult.hasErrors()){
            UserDataDto dto = UserDataDto.builder()
                    .name(form.getName())
                    .password(form.getPassword())
                    .mail(form.getMail())
                    .build();
            if(userService.saveUser(dto)) {
                return "redirect:/signIn";
            }else {
                return "redirect:/signUp";
            }
        }

        System.out.println(form);
        System.out.println(bindingResult.getAllErrors());
        model.addAttribute("signUpForm", form);
        return "signUp";

    }

}
