package server.businesslogic.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.entity.message.MessageModel;
import server.entity.queue.QueueModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryJpaImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public MessageModel addMessage(MessageModel message) {
        entityManager.persist(message);
        return message;
    }

    @Override
    @Transactional
    public void update(MessageModel message) {
        entityManager.merge(message);
    }

    @Override
    @Transactional
    public void complete(MessageModel message) {
       entityManager.createQuery("UPDATE MessageModel m SET m.completed = true WHERE m.id=:id")
                .setParameter("id", message.getId()).executeUpdate();
    }

    @Override
    @Transactional
    public void accept(MessageModel message) {
        entityManager.createQuery("UPDATE MessageModel m SET m.received = true WHERE m.id=:id")
                .setParameter("id", message.getId()).executeUpdate();
    }

    @Override
    @Transactional
    public List<MessageModel> findAllNotCompleted(QueueModel queue) {
       return entityManager.createQuery("SELECT m FROM MessageModel m WHERE m.queue =:queue and " +
                "m.completed = false or m.received = false ", MessageModel.class).
               setParameter("queue", queue).getResultList();
    }

    @Override
    public boolean isExist(String id) {
        Long count = (Long)entityManager.createQuery("SELECT COUNT(m) FROM MessageModel m WHERE m.id=:id")
                .setParameter("id", id).getSingleResult();
        return count.equals(1L);
    }

    @Override
    public Optional<MessageModel> findMessage(String id) {
        return Optional.ofNullable(entityManager.createQuery("SELECT m FROM MessageModel m WHERE m.id=:id", MessageModel.class)
                .setParameter("id", id).getSingleResult());
    }
}
