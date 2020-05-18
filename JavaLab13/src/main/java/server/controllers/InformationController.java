package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import server.bl.services.UserService;
import server.entities.user.UserDto;
import server.entities.userInformation.InformationDto;

import java.util.Optional;

@Controller
public class InformationController {

    @Autowired
    private UserService userService;

    @GetMapping("/info/{userId}")
    public String getPage(Model model, @PathVariable Long userId){
        if(userId == null){
            return "redirect:/signIn";
        }
        Optional<InformationDto> optionalInformationDto = userService.getInformation(userId);
        Optional<UserDto> userDtoOptional = userService.findUserById(userId);
        if(!optionalInformationDto.isPresent()){
            return "redirect:/signIn";
        }
        if(!userDtoOptional.isPresent()){
            return "redirect:/signIn";
        }

        model.addAttribute("info", optionalInformationDto.get());
        model.addAttribute("userData", userDtoOptional.get());
        return "info";
    }
}
