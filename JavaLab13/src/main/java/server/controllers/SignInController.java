package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import server.bl.services.UserService;
import server.entities.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SignInController {

    @Autowired
    private UserService userService;

    @PostMapping("/signIn")
    public String signIn(UserDto userDto, HttpServletRequest request){
        if(userService.isExist(userDto.getMail(), userDto.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", userDto);
            return "redirect:/files";
        }else {
            return "redirect:/signIn";
        }
    }

    @GetMapping("/signIn")
    public String getPage(){
        return "signIn";
    }
}
