package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import server.bl.services.RoomService;
import server.bl.services.UserService;
import server.entities.dto.RoomDto;
import server.entities.dto.UserDto;
import server.entities.model.RoomModel;
import server.jwt.Tokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomsController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private Tokenizer tokenizer;

    @PostMapping("/rooms")
    public String createRoom(HttpServletResponse resp, HttpServletRequest req) {

        Optional<UserDto> optional = getUser(req);
        if(!optional.isPresent()){
            return "redirect:/signIn";
        }

        RoomDto roomDto = roomService.createRoom(optional.get());
        return "redirect:/rooms";
    }

    @GetMapping("/rooms")
    public String getPage(HttpServletRequest req, Model model){

        Optional<UserDto> optional = getUser(req);
        if(!optional.isPresent()){
            return "redirect:/signIn";
        }

        List<RoomDto> roomDtoList = roomService.findAllRooms();
        model.addAttribute("rooms", roomDtoList);
        return "rooms";
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
