package server.addition.chat.bl.repository;

import server.addition.chat.entity.model.ChatModel;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {

    void addChat(ChatModel chatModel);
    Optional<ChatModel> findChat(Long id);
    List<ChatModel> findAllChatsByUserId(Long id);

}
