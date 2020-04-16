package server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import server.addition.chat.bl.service.ChatService;
import server.addition.chat.entity.dto.MessageDto;
import server.entities.user.model.UserDataModel;
import server.security.details.UserDetailImpl;
import server.security.jwt.details.UserDetailsJwtImpl;

import java.util.List;

@Controller
public class NewMessageController {


    @Autowired
    private ChatService chatService;

    @GetMapping("/new_messages")
    @PreAuthorize("isAuthenticated()")
    public String findNewMessages(Authentication authentication, Model model) {

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        UserDataModel userDataModel = userDetail.getUser();
        Long userId = userDataModel.getId();


        List<List<MessageDto>> lists = chatService.findAllNewMessages(userId);

        model.addAttribute("userData", userDataModel);
        model.addAttribute("messages", lists);

        return "new_messages";


    }
}
