package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import server.addition.chat.bl.service.ChatService;
import server.addition.chat.lobby.Room;
import server.entities.user.model.UserDataModel;
import server.security.details.UserDetailImpl;

@Controller
public class ChatPageController {

    @Autowired
    private ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat/{user_id:.+}")
    public String getPage(Model model, Authentication authentication,
                          @PathVariable("user_id") Long id){

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        UserDataModel userDataModel = userDetail.getUser();

        Room room = chatService.getRoom(id, userDataModel.getId());
        System.out.println(id + " " + userDataModel.getId());

        model.addAttribute("room", room);
        model.addAttribute("userData", userDataModel);

        return "chat";

    }
}
