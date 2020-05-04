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
import java.util.Optional;

@Controller
public class SendController {

    @Autowired
    private Converter dispatcher;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate template;


    @MessageMapping("/send")
    public void addNewTask(String json) {
        try {
            Command command = objectMapper.readValue(json, Command.class);
            Object response = dispatcher.send(command);
            template.convertAndSend("/topic/producer/" + command.getQueueName(), objectMapper.writeValueAsString(response));
            Optional<MessageGeneralDto> optional = dispatcher.findMessage(command.getQueueName(), command.getMessageId());

            if (optional.isPresent()) {
                template.convertAndSend("/topic/consumer/" + command.getQueueName(), objectMapper.writeValueAsString(optional.get()));
            }else {
                throw new IllegalArgumentException("message not found");
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
