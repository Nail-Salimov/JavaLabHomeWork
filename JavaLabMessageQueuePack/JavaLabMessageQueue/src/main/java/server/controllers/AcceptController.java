package server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import server.businesslogic.dispatchers.Converter;
import server.entity.command.Command;

import java.io.IOException;

@Controller
public class AcceptController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private Converter dispatcher;

    @MessageMapping("/accept")
    public void accept(String json){
        try {
            Command command = objectMapper.readValue(json, Command.class);
            Object response = dispatcher.accept(command);
            template.convertAndSend("/topic/consumer/" + command.getQueueName(), objectMapper.writeValueAsString(response));
        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }
    }
}
