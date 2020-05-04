package server.additions.websockets.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import server.businesslogic.dispatchers.Converter;
import server.entity.command.Command;
import server.entity.message.MessageGeneralDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    @Autowired
    private Converter dispatcher;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
            Command command = objectMapper.readValue((String) message.getPayload(), Command.class);
            System.out.println(command.getCommand());
            System.out.println(message.getPayload());
            String queueName = command.getQueueName();

            if (command.getCommand().equals("subscribe")) {


                sessions.put(queueName, session);

                synchronized (sessions.get(queueName)) {
                    List<MessageGeneralDto> list = dispatcher.getNewMessages(command);
                    if (list.isEmpty()) {
                        sessions.get(queueName).wait();
                    }
                    list = dispatcher.getNewMessages(command);

                    for (MessageGeneralDto generalDto : list) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(generalDto)));
                    }


                }

            } else if (command.getCommand().equals("send")) {
                Object response = dispatcher.send(command);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));

                if (sessions.containsKey(queueName)) {
                    synchronized (sessions.get(queueName)) {
                        WebSocketSession consumerSession = sessions.get(queueName);
                        Optional<MessageGeneralDto> optional = dispatcher.findMessage(queueName, command.getMessageId());
                        if(consumerSession.isOpen() && optional.isPresent()){
                            consumerSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(optional.get())));
                        }
                        //sessions.get(queueName).sendMessage(new TextMessage(objectMapper.writeValueAsString(com)));
                    }
                }

            } else if (command.getCommand().equals("accept")) {
                command.setQueueName(getQueue(session));
                Object response = dispatcher.accept(command);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));

            } else if (command.getCommand().equals("completed")) {
                command.setQueueName(getQueue(session));
                Object response = dispatcher.complete(command);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
            } else {
                throw new IllegalArgumentException("command not found");
            }

        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String getQueue(WebSocketSession socketSession) {
        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            if (entry.getValue().equals(socketSession)) {
                return entry.getKey();
            }
        }
        return null;
    }


}



