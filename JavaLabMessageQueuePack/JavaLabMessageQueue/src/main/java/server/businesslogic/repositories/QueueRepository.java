package server.businesslogic.repositories;

import server.entity.queue.QueueModel;

import java.util.Optional;

public interface QueueRepository {
    void addQueue(QueueModel queue);
    boolean isExist(String queueName);
    Optional<QueueModel> findQueue(String queueName);
}
