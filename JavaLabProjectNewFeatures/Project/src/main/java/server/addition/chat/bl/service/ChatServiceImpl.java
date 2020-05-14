package server.addition.chat.bl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.addition.chat.bl.repository.ChatRepository;
import server.addition.chat.bl.repository.MessageRepository;
import server.addition.chat.entity.dto.MessageDto;
import server.addition.chat.entity.model.ChatModel;
import server.addition.chat.entity.model.Member;
import server.addition.chat.entity.model.MessageModel;
import server.addition.chat.entity.model.NotifyModel;
import server.addition.chat.lobby.Avatar;
import server.addition.chat.lobby.Lobby;
import server.addition.chat.lobby.Room;

import java.util.*;

@Component
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private Lobby lobby;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Optional<ChatModel> findChat(Long id) {
        Optional<ChatModel> optional = chatRepository.findChat(id);
        return optional;
    }

    @Override
    public Room getRoom(Long id) {
        Optional<ChatModel> optional = chatRepository.findChat(id);

        if (optional.isPresent()) {
            ChatModel chatModel = optional.get();
            Optional<Room> optionalRoom = lobby.findRoom(chatModel.getId());

            return optionalRoom.orElseGet(() -> lobby.createRoom(optional.get()));

        }
        throw new IllegalArgumentException("room with id is not exist");

    }

    @Override
    public Room getRoom(Long firstId, Long secondId) {


        Optional<Room> optional = lobby.findRoom(new long[]{firstId, secondId});
        if (optional.isPresent()) {
            return optional.get();
        } else {

            ChatModel chatModel = new ChatModel();

            Set<Member> members = new HashSet<>();
            members.add(Member.builder()
                    .memberId(firstId)
                    .chatModel(chatModel)
                    .build());
            members.add(Member.builder()
                    .memberId(secondId)
                    .chatModel(chatModel)
                    .build());



            chatModel.setMembers(members);

            chatRepository.addChat(chatModel);

            return lobby.createRoom(chatModel);
        }
    }

    @Override
    public List<Avatar> sendMessage(Long chatId, MessageDto messageDto) {
        Room room = getRoom(chatId);
        Optional<ChatModel> optional = findChat(chatId);

        if (!optional.isPresent()) {
            throw new IllegalStateException("Database has not rooms data");
        }

        ChatModel chatModel = optional.get();

        MessageModel messageModel = createMessage(messageDto, room, chatModel);

        List<Avatar> avatars = room.getAvatars();
        messageRepository.addMessage(messageModel);
        return avatars;
    }

    @Override
    public List<MessageDto> findNewMessages(Room room, Long id) {
        List<NotifyModel> modelList = messageRepository.findNewMessageWithReading(room.getId(), id);
        List<MessageDto> messages = new LinkedList<>();
        for (NotifyModel p : modelList) {
            MessageDto messageDto = new MessageDto();

            messageDto.setText(p.getMessage().getText());
            messageDto.setSenderId(p.getReceiverId());
            messages.add(messageDto);
        }
        return messages;
    }

    private List<MessageDto> findNewMessagesWithoutReading(Long roomId, Long id) {
        List<NotifyModel> modelList = messageRepository.findNewMessageWithoutReading(roomId, id);
        List<MessageDto> messages = new LinkedList<>();
        for (NotifyModel p : modelList) {
            MessageDto messageDto = new MessageDto();

            messageDto.setText(p.getMessage().getText());
            messages.add(messageDto);
            messageDto.setSenderId(p.getReceiverId());
        }
        return messages;
    }

    @Override
    public List<List<MessageDto>> findAllNewMessages(Long id) {
        List<ChatModel> chatModelList = chatRepository.findAllChatsByUserId(id);
        List<List<MessageDto>> lists = new LinkedList<>();

        for (ChatModel model : chatModelList) {
            lists.add(findNewMessagesWithoutReading(model.getId(), id));
        }
        return lists;

    }

    private MessageModel createMessage(MessageDto messageDto, Room room, ChatModel chatModel) {
        MessageModel message = new MessageModel();
        message.setChatModel(chatModel);
        message.setSenderId(messageDto.getSenderId());
        List<Avatar> avatarList = room.getAvatars();
        List<NotifyModel> paths = new LinkedList<>();

        for (Avatar a : avatarList) {
            paths.add(NotifyModel.builder()
                    .receiverId(a.getId())
                    .state(false)
                    .message(message)
                    .build());
        }

        message.setPaths(paths);
        message.setText(messageDto.getText());
        return message;

    }
}
