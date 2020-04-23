package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import server.bl.services.UserService;
import server.entities.dto.UserDto;
import server.jwt.Tokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    public Tokenizer tokenizer;

    @GetMapping("/signUp")
    public String getPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(Model model, @RequestParam("name") String name,
                         @RequestParam("mail") String mail, @RequestParam("password") String password,
                         HttpServletRequest request, HttpServletResponse response) {


        UserDto userDto = UserDto.builder()
                .mail(mail)
                .name(name)
                .password(password)
                .build();

        Optional<UserDto> optional = userService.saveUser(userDto);

        if (optional.isPresent()) {
            Cookie cookie = new Cookie("token", tokenizer.getToken(userDto.getName(), userDto.getMail()));
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            return "redirect:/rooms";
        }

        return "redirect:/signUp";

    }
}
