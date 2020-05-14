package server.addition.chat.bl.repository;

import server.addition.chat.entity.model.MessageModel;
import server.addition.chat.entity.model.NotifyModel;

import java.util.List;

public interface MessageRepository {
    void addMessage(MessageModel message);
    void updatePath(NotifyModel path);

    List<NotifyModel> findNewMessageWithReading(Long roomId, Long userId);

    List<NotifyModel> findNewMessageWithoutReading(Long roomId, Long userId);
}
