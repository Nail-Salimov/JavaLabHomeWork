package server.businesslogic.dispatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.businesslogic.services.TaskService;
import server.entity.command.Body;
import server.entity.command.Command;
import server.entity.command.Response;
import server.entity.message.MessageDto;
import server.entity.message.MessageGeneralDto;
import server.entity.queue.QueueDto;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class ConverterImpl implements Converter {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Object send(Command command) {
        try {
            String bodyJson = objectMapper.writeValueAsString(command.getBody());
            Optional<QueueDto> optionalQueue = taskService.findQueue(command.getQueueName());

            if (!optionalQueue.isPresent()) {
                return objectMapper.writeValueAsString(new Response("queue is not exist"));
            }

            MessageDto messageDto = MessageDto.builder()
                    .body(bodyJson)
                    .completed(false)
                    .received(false)
                    .queue(optionalQueue.get())
                    .build();

            Optional<MessageDto> optionalMessageDto = taskService.addMessage(messageDto);
            if (optionalMessageDto.isPresent()) {
                MessageDto dto = optionalMessageDto.get();
                command.setMessageId(dto.getId());
                return new Response(dto.getId());
            } else {
                return new Response("queue is not exist");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Object accept(Command command) {
        try {
            Optional<QueueDto> optionalQueue = taskService.findQueue(command.getQueueName());

            if (!optionalQueue.isPresent()) {
                return objectMapper.writeValueAsString(new Response("queue is not exist"));
            }

            MessageDto messageDto = MessageDto.builder()
                    .completed(false)
                    .received(true)
                    .queue(optionalQueue.get())
                    .id(command.getMessageId())
                    .build();
            if (taskService.receive(messageDto)) {
                return objectMapper.writeValueAsString(new Response("success"));
            } else {
                return new Response("fail");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Object complete(Command command) {
        try {
            Optional<QueueDto> optionalQueue = taskService.findQueue(command.getQueueName());

            if (!optionalQueue.isPresent()) {
                return objectMapper.writeValueAsString(new Response("queue is not exist"));
            }

            MessageDto messageDto = MessageDto.builder()
                    .completed(true)
                    .received(true)
                    .queue(optionalQueue.get())
                    .id(command.getMessageId())
                    .build();

            if (taskService.completed(messageDto)) {
                return objectMapper.writeValueAsString(new Response("complete success"));
            } else {
                return new Response("complete fail");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<MessageGeneralDto> getNewMessages(Command command) {
        Optional<QueueDto> optionalQueue = taskService.findQueue(command.getQueueName());
        return optionalQueue.map(queueDto -> getGeneralListDto(taskService.getNotCompleted(queueDto)))
                .orElse(null);
    }

    @Override
    public Optional<MessageGeneralDto> findMessage(String queueName, String id) {
        try {
            Optional<MessageDto> optionalMessageDto = taskService.findMessage(id);

            if (!optionalMessageDto.isPresent()) {
                return Optional.empty();
            }
            MessageDto dto = optionalMessageDto.get();
            if (dto.getQueue().getQueueName().equals(queueName)) {
                return Optional.of(MessageGeneralDto.builder()
                        .command("receive")
                        .messageId(dto.getId())
                        .queueName(dto.getQueue().getQueueName())
                        .body(objectMapper.readValue(dto.getBody(), Body.class))
                        .build());
            }else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<MessageGeneralDto> getGeneralListDto(List<MessageDto> list) {
        List<MessageGeneralDto> generalList = new LinkedList<>();

        try {
            for (MessageDto dto : list) {
                generalList.add(MessageGeneralDto.builder()
                        .command("receive")
                        .messageId(dto.getId())
                        .queueName(dto.getQueue().getQueueName())
                        .body(objectMapper.readValue(dto.getBody(), Body.class))
                        .build());
            }
            return generalList;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
