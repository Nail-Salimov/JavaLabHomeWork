package server.addition.chat.bl.service;

import server.addition.chat.entity.dto.MessageDto;
import server.addition.chat.entity.model.ChatModel;
import server.addition.chat.lobby.Avatar;
import server.addition.chat.lobby.Room;

import javax.mail.Message;
import java.util.List;
import java.util.Optional;

public interface ChatService {

    Optional<ChatModel> findChat(Long id);
    Room getRoom(Long id);
    Room getRoom(Long firstId, Long secondId);

    List<Avatar> sendMessage(Long chatId, MessageDto messageDto);
    List<MessageDto> findNewMessages(Room room, Long id);
    List<List<MessageDto>> findAllNewMessages(Long id);
}
