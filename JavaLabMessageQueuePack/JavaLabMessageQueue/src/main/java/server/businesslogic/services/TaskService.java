package server.businesslogic.services;

import server.entity.message.MessageDto;
import server.entity.message.MessageModel;
import server.entity.queue.QueueDto;
import server.entity.queue.QueueModel;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<MessageDto> addMessage(MessageDto messageDto);
    boolean receive(MessageDto messageDto);
    boolean completed(MessageDto messageDto);
    List<MessageDto> getNotCompleted(QueueDto queueDto);

    boolean addQueue(QueueDto queueDto);
    Optional<QueueDto> findQueue(String queueName);
    Optional<MessageDto> findMessage(String id);
}
