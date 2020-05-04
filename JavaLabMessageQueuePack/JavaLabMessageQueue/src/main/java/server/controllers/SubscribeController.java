package server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import server.businesslogic.dispatchers.Converter;
import server.entity.command.Command;
import server.entity.message.MessageGeneralDto;

import java.io.IOException;
import java.util.List;

@Controller
public class SubscribeController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private Converter dispatcher;

    @MessageMapping("/subscribe")
    public void getAllNotConfirmedMessages(String json){
        try {
            Command command = objectMapper.readValue(json, Command.class);
            List<MessageGeneralDto> list =  dispatcher.getNewMessages(command);

            for (MessageGeneralDto dto : list){
                template.convertAndSend("/topic/consumer/" + command.getQueueName(), objectMapper.writeValueAsString(dto));
            }

        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }
    }
}
