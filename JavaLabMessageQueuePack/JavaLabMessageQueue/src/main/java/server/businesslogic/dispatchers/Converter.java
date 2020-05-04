package server.businesslogic.dispatchers;

import server.entity.command.Command;
import server.entity.message.MessageGeneralDto;

import java.util.List;
import java.util.Optional;

public interface Converter {

    Object send(Command command);
    Object accept(Command command);
    Object complete(Command command);
    List<MessageGeneralDto> getNewMessages(Command command);
    Optional<MessageGeneralDto> findMessage(String queueName, String id);
}
