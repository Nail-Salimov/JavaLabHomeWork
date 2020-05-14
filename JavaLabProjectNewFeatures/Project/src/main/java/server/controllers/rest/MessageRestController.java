package server.controllers.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.addition.chat.bl.service.ChatService;
import server.addition.chat.entity.dto.MessageDto;
import server.addition.chat.lobby.Avatar;
import server.addition.chat.lobby.Room;
import server.security.jwt.details.UserDetailsJwtImpl;

import java.util.List;

@RestController
public class MessageRestController {

    @Autowired
    private ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/messages/{chat_id:.+}")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto messageDto,
                                                 @PathVariable("chat_id") Long chatId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsJwtImpl userDetailsJwt = (UserDetailsJwtImpl) authentication.getDetails();
        messageDto.setSenderId(userDetailsJwt.getUserId());

        List<Avatar> avatars = chatService.sendMessage(chatId, messageDto);

        for (Avatar avatar : avatars) {
            synchronized (avatar) {
                avatar.notifyAll();
            }
        }
        return ResponseEntity.ok().build();


    }

    @SneakyThrows
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/messages/{chat_id:.+}")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(@PathVariable("chat_id") Long chatId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsJwtImpl userDetailsJwt = (UserDetailsJwtImpl) authentication.getDetails();
        Long userId =userDetailsJwt.getUserId();

        Room room = chatService.getRoom(chatId);
        Avatar myAvatar = room.findMyAvatar(userId);
        List<MessageDto> messages = null;

        synchronized (myAvatar) {
            messages = chatService.findNewMessages(room, userId);
            if (messages.isEmpty()) {
                myAvatar.wait();
                messages = chatService.findNewMessages(room, userId);
            }
            System.out.println("Нашел новое сообщение " + messages.size());
        }

        System.out.println("отправил их " + messages.size());
        return ResponseEntity.ok().body(messages);
    }

}
