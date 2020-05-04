package server.businesslogic.repositories;

import server.entity.message.MessageModel;
import server.entity.queue.QueueModel;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    MessageModel addMessage(MessageModel message);
    void update(MessageModel message);
    void complete(MessageModel message);
    void accept(MessageModel message);
    List<MessageModel> findAllNotCompleted(QueueModel queueModel);

    boolean isExist(String id);
    Optional<MessageModel> findMessage(String id);

}
