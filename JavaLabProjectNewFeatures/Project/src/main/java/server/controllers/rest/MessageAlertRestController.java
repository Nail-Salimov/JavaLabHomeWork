package server.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.addition.chat.bl.service.ChatService;
import server.addition.chat.entity.dto.MessageDto;
import server.security.jwt.details.UserDetailsJwtImpl;

import java.util.List;

@RestController
public class MessageAlertRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/api/alert_new_messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<List<MessageDto>>> findNewMessages(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsJwtImpl userDetailsJwt = (UserDetailsJwtImpl) authentication.getDetails();
        Long userId =userDetailsJwt.getUserId();


        List<List<MessageDto>> lists = chatService.findAllNewMessages(userId);

        return ResponseEntity.ok().body(lists);


    }
}
