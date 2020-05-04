package server.businesslogic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.businesslogic.repositories.MessageRepository;
import server.businesslogic.repositories.MessageRepositoryJpaImpl;
import server.businesslogic.repositories.QueueRepository;
import server.entity.message.MessageDto;
import server.entity.message.MessageModel;
import server.entity.queue.QueueDto;
import server.entity.queue.QueueModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private QueueRepository queueRepository;


    @Override
    public Optional<MessageDto> addMessage(MessageDto messageDto) {
        MessageModel messageModel = castToMessageModel(messageDto);
        Optional<QueueModel> optionalQueueModel =
                getQueueModel(messageDto.getQueue().getQueueName());

        if (optionalQueueModel.isPresent()) {
            messageModel.setQueue(optionalQueueModel.get());
            messageModel = messageRepository.addMessage(messageModel);
            return Optional.of(castToMessageDto(messageModel));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean receive(MessageDto messageDto) {
        MessageModel messageModel = castToMessageModel(messageDto);
        Optional<QueueModel> optionalQueueModel =
                getQueueModel(messageDto.getQueue().getQueueName());

        if (optionalQueueModel.isPresent()) {
            Optional<MessageModel> optionalMessageModel = messageRepository.findMessage(messageDto.getId());
            if(!optionalMessageModel.isPresent()){
                return false;
            }
            messageModel = optionalMessageModel.get();
            messageModel.setReceived(true);
            messageRepository.accept(messageModel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean completed(MessageDto messageDto) {
        MessageModel messageModel = castToMessageModel(messageDto);
        Optional<QueueModel> optionalQueueModel =
                getQueueModel(messageDto.getQueue().getQueueName());

        if (optionalQueueModel.isPresent()) {
            Optional<MessageModel> optionalMessageModel = messageRepository.findMessage(messageDto.getId());
            if(!optionalMessageModel.isPresent()){
                return false;
            }
            messageModel = optionalMessageModel.get();

            if(messageModel.getCompleted().equals(true) || messageDto.getReceived().equals(false)){
                return false;
            }
            messageModel.setCompleted(true);
            messageModel.setReceived(true);
            messageRepository.complete(messageModel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MessageDto> getNotCompleted(QueueDto queueDto) {
        Optional<QueueModel> optionalQueueModel = getQueueModel(queueDto.getQueueName());

        if (optionalQueueModel.isPresent()) {
            List<MessageModel> models = messageRepository.findAllNotCompleted(optionalQueueModel.get());
            List<MessageDto> messageDtoList = new LinkedList<>();

            for (MessageModel model : models) {
                messageDtoList.add(castToMessageDto(model));
            }
            return messageDtoList;

        } else {
            return null;
        }
    }

    @Override
    public boolean addQueue(QueueDto queueDto) {
        QueueModel queueModel = castToQueueModel(queueDto);

        if (!queueRepository.isExist(queueModel.getQueueName())) {
            queueRepository.addQueue(queueModel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<QueueDto> findQueue(String queueName) {
        Optional<QueueModel> queueModel = getQueueModel(queueName);
        return queueModel.map(this::castToQueueDto);
    }

    @Override
    public Optional<MessageDto> findMessage(String id) {
        Optional<MessageModel> optionalMessageModel = messageRepository.findMessage(id);
        return optionalMessageModel.map(this::castToMessageDto);

    }

    private Optional<QueueModel> getQueueModel(String queueName) {
        if (queueRepository.isExist(queueName)) {
            return Optional.of(queueRepository.findQueue(queueName).get());
        } else {
            return Optional.empty();
        }
    }

    private QueueModel castToQueueModel(QueueDto queueDto) {
        return QueueModel.builder()
                .id(queueDto.getId())
                .queueName(queueDto.getQueueName())
                .build();
    }

    private MessageModel castToMessageModel(MessageDto messageDto) {
        return MessageModel.builder()
                .body(messageDto.getBody())
                .id(messageDto.getId())
                .completed(messageDto.getCompleted())
                .queue(castToQueueModel(messageDto.getQueue()))
                .received(messageDto.getReceived())
                .build();
    }

    private QueueDto castToQueueDto(QueueModel queueModel) {
        return QueueDto.builder()
                .id(queueModel.getId())
                .queueName(queueModel.getQueueName())
                .build();
    }

    private MessageDto castToMessageDto(MessageModel messageModel) {
        return MessageDto.builder()
                .body(messageModel.getBody())
                .id(messageModel.getId())
                .completed(messageModel.getCompleted())
                .queue(castToQueueDto(messageModel.getQueue()))
                .received(messageModel.getReceived())
                .build();
    }
}
