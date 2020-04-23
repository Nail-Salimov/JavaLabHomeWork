package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import server.bl.services.RoomService;
import server.bl.services.UserService;
import server.entities.dto.RoomDto;
import server.entities.dto.UserDto;
import server.jwt.Tokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private Tokenizer tokenizer;

    @GetMapping("/room/{room-id:.+}")
    public String getPage(Model model, HttpServletRequest req,
                          @PathVariable(value = "room-id", required = false) Long roomId){

        Optional<UserDto> optionalUser = getUser(req);
        if(!optionalUser.isPresent()){
            return "redirect:/signIn";
        }

        if(roomId == null){
            return "redirect:/rooms";
        }
        Optional<RoomDto> optionalRoomDto = roomService.findRoomById(roomId);
        if(!optionalRoomDto.isPresent()){
            return "redirect:/rooms";
        }

        RoomDto roomDto = optionalRoomDto.get();
        UserDto userDto = optionalUser.get();
        model.addAttribute("roomData", roomDto);
        model.addAttribute("userData", userDto);
        roomService.join(roomDto, userDto);

        Cookie[] cookies = req.getCookies();
        Cookie cookie = null;
        for (Cookie c: cookies){
            if(c.getName().equals("token")){
                cookie = c;
                break;
            }
        }
        String token = cookie.getValue();
        model.addAttribute("token", token);

        return "room";

    }


    public Optional<UserDto> getUser(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        Cookie cookie = null;

        if (cookies != null) {
            for (Cookie value : cookies) {
                cookie = value;
                if (cookie.getName().equals("token")) {
                    break;
                }
            }
        }

        if (cookie == null) {
            return  Optional.empty();
        }

        Optional<UserDto> optional = tokenizer.getUser(cookie.getValue());
        if (!optional.isPresent()) {
            return  Optional.empty();
        }
        UserDto userDto = optional.get();

        optional = userService.findUserByMail(userDto.getMail());

        return optional;

    }

}
