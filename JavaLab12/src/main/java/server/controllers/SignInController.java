package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.bl.services.UserService;
import server.entities.dto.UserDto;
import server.jwt.Tokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class SignInController {

    @Autowired
    private UserService userService;

    @Autowired
    private Tokenizer tokenizer;

    @GetMapping("/signIn")
    public String getPage(){
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(Model model,
                         @RequestParam("mail") String mail, @RequestParam("password") String password,
                         HttpServletRequest request, HttpServletResponse response) {


        UserDto userDto = UserDto.builder()
                .mail(mail)
                .password(password)
                .build();

        if (userService.authorize(userDto)) {
            Optional<UserDto> optional = userService.findUserByMail(userDto.getMail());
            if(!optional.isPresent()){
                throw new IllegalArgumentException("user not found");
            }

            userDto = optional.get();
            Cookie cookie = new Cookie("token", tokenizer.getToken(userDto.getName(), userDto.getMail()));
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            return "redirect:/rooms";
        }

        return "redirect:/signIn";

    }
}
