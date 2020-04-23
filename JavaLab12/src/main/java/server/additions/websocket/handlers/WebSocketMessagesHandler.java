package server.additions.websocket.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import server.bl.services.RoomService;
import server.bl.services.UserService;
import server.entities.dto.MessageDto;
import server.entities.dto.UserDto;
import server.jwt.Tokenizer;

import java.io.IOException;
import java.util.*;


@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<Long, Map<Long, List<WebSocketSession>>> rooms = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private Tokenizer tokenizer;

    @Autowired
    private UserService userService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("sad");

        String messageText = (String) message.getPayload();
        MessageDto messageFromJson = objectMapper.readValue(messageText, MessageDto.class);


        Optional<UserDto> optionalUserDto = userService.findUserById(messageFromJson.getFrom());

        if (!optionalUserDto.isPresent()) {
            throw new IllegalArgumentException("user is not exist");
        }

        UserDto user = optionalUserDto.get();

        checkToken(messageFromJson, user);

        if (!rooms.containsKey(messageFromJson.getRoomId())) {
            Map<Long, List<WebSocketSession>> room = new HashMap<>();
            List<WebSocketSession> list = new LinkedList<>();
            list.add(session);
            room.put(user.getId(), list);
            rooms.put(messageFromJson.getRoomId(), room);
        }

        Map<Long, List<WebSocketSession>> room = rooms.get(messageFromJson.getRoomId());

        if (!room.containsKey(user.getId())) {
            List<WebSocketSession> list = new LinkedList<>();
            list.add(session);
            room.put(user.getId(), list);
        }

        List<WebSocketSession> sessions = room.get(user.getId());

        if (!sessions.contains(session)) {
            sessions.add(session);
        }

        if (messageFromJson.getType().equals("message")) {
            for (List<WebSocketSession> listSessions : room.values()) {

                List<WebSocketSession> remove = new LinkedList<>();
                for (WebSocketSession s : listSessions) {
                    if (!s.isOpen()) {
                        s.close();
                        remove.add(s);
                    } else {
                        s.sendMessage(message);
                    }
                }

                for (WebSocketSession r : remove) {
                    listSessions.remove(r);
                }
            }
        }
    }

    public void checkToken(MessageDto message, UserDto user) {

        if (message.getType().equals("login")) {
            Optional<UserDto> userDto = tokenizer.getUser(message.getText());

            if (!userDto.isPresent()) {
                System.out.println(1);
                throw new IllegalArgumentException("token is invalid");
            }

            if (!user.getMail().equals(userDto.get().getMail())) {
                System.out.println(1);
                throw new IllegalArgumentException("token is invalid");
            }
        }
    }

}



